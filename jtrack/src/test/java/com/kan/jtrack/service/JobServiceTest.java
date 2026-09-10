package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.request.JobStatusUpdateRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.entity.Job;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.JobMapper;
import com.kan.jtrack.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    private static final Integer ID_1 = 1;
    private static final Integer SPRINT_ID_1 = 1;
    private static final String NAME_1 = "Main Job";
    private static final String DESCRIPTION_1 = "Main Job Description";
    private static final String TYPE_CODE_1 = "TASK";
    private static final String PRIORITY_CODE_1 = "HIGH";
    private static final String STATUS_CODE_1 = "OPEN";
    private static final String ASSIGNED_TO_1 = "Kan Ranganathan";
    private static final Double ESTIMATED_HOURS_1 = 40.0;
    private static final Double ACTUAL_HOURS_1 = 4.0;
    private static final Integer PARENT_ID_1_NULL = null;
    private static final LocalDateTime CREATED_AT_1 = LocalDateTime.of(2026, 6, 15, 0, 0);
    private static final String CREATED_BY_1 = "user-1";
    private static final LocalDateTime UPDATED_AT_1 = LocalDateTime.of(2026, 7, 23, 0, 0);
    private static final String UPDATED_BY_1 = "user-2";

    private static final Integer ID_2 = 2;
    private static final Integer SPRINT_ID_2 = 1;
    private static final String NAME_2 = "Sub Job";
    private static final String DESCRIPTION_2 = "Sub Job Description";
    private static final String TYPE_CODE_2 = "SUB_TASK";
    private static final String PRIORITY_CODE_2 = "MEDIUM";
    private static final String STATUS_CODE_2 = "IN_PROGRESS";
    private static final String ASSIGNED_TO_2 = "Steve Smith";
    private static final Double ESTIMATED_HOURS_2 = 12.0;
    private static final Double ACTUAL_HOURS_2 = 4.0;
    private static final Integer PARENT_ID_2 = 1;
    private static final LocalDateTime CREATED_AT_2 = LocalDateTime.of(2026, 7, 15, 0, 0);
    private static final String CREATED_BY_2 = "user-1";

    private static final Integer ID_UNKNOWN = 999;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @Mock
    private UserService userService;

    @Mock
    private AuditEntityService auditEntityService;

    @InjectMocks
    private JobService jobService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenJobsExist_shouldReturnJobResponseList() {
        Job job1 = generateJob1();
        Job job2 = generateJob2();

        JobResponse jobResponse1 = generateJobResponse1();
        JobResponse jobResponse2 = generateJobResponse2();

        UserResponse user1 = generateUserResponse1();
        UserResponse user2 = generateUserResponse2();

        when(jobRepository.findAll()).thenReturn(List.of(job1, job2));
        when(userService.getUserByIdIgnoreBlank(job1.getAssignedTo())).thenReturn(user1);
        when(userService.getUserByIdIgnoreBlank(job2.getAssignedTo())).thenReturn(user2);
        when(jobMapper.toJobResponse(job1, user1)).thenReturn(jobResponse1);
        when(jobMapper.toJobResponse(job2, user2)).thenReturn(jobResponse2);

        List<JobResponse> result = jobService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(jobResponse1));
        assertTrue(result.contains(jobResponse2));
    }

    @Test
    void getAll_whenNoJobsExist_shouldReturnEmptyList() {
        when(jobRepository.findAll()).thenReturn(List.of());

        List<JobResponse> result = jobService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> jobService.getById(id));
    }

    @Test
    void getById_whenJobExists_shouldReturnJobResponse() {
        Job job = generateJob1();
        JobResponse jobResponse = generateJobResponse1();
        UserResponse userResponse = generateUserResponse1();

        when(jobRepository.findById(ID_1)).thenReturn(of(job));
        when(userService.getUserByIdIgnoreBlank(job.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(job, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.getById(ID_1);

        assertNotNull(result);
        assertEquals(jobResponse, result);
    }

    @Test
    void getById_whenNoJobExists_shouldThrowResourceNotFoundException() {
        when(jobRepository.findById(ID_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> jobService.getById(ID_UNKNOWN));
    }

    // ============ create() Tests ============

    @ParameterizedTest
    @NullSource
    void create_whenJobRequestIsNull_shouldThrowValidationException(JobRequest jobRequest) {
        assertThrows(ValidationException.class, () -> jobService.create(jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenNameIsNullOrBlank_shouldThrowValidationException(String name) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setName(name);

        assertThrows(ValidationException.class, () -> jobService.create(jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenTypeCodeIsNullOrBlank_shouldThrowValidationException(String typeCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setTypeCode(typeCode);

        assertThrows(ValidationException.class, () -> jobService.create(jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenPriorityCodeIsNullOrBlank_shouldThrowValidationException(String priorityCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setPriorityCode(priorityCode);

        assertThrows(ValidationException.class, () -> jobService.create(jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenStatusCodeIsNullOrBlank_shouldThrowValidationException(String statusCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setStatusCode(statusCode);

        assertThrows(ValidationException.class, () -> jobService.create(jobRequest));
    }

    @Test
    void create_whenParentIdIsNull_shouldSaveAndReturnResponse() {
        JobRequest jobRequest = generateJobRequest1();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job job = generateJob1();
        job.setId(null); // Ensure the ID is null before saving
        Job savedJob = generateJob1();

        JobResponse jobResponse = generateJobResponse1();
        UserResponse userResponse = generateUserResponse1();

        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(jobMapper.toJob(jobRequest, auditEntityRequest)).thenReturn(job);
        when(jobRepository.save(job)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.create(jobRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);

        verify(jobRepository).save(job);
        verify(jobRepository, never()).refreshParentJobEstimatedHours(PARENT_ID_1_NULL);
        verify(jobRepository, never()).refreshParentJobActualHours(PARENT_ID_1_NULL);
    }

    @Test
    void create_whenParentIdIsNotNull_shouldSaveAndReturnResponse() {
        JobRequest jobRequest = generateJobRequest2();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job job = generateJob2();
        job.setId(null); // Ensure the ID is null before saving
        Job savedJob = generateJob2();

        JobResponse jobResponse = generateJobResponse2();
        UserResponse userResponse = generateUserResponse2();
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(jobMapper.toJob(jobRequest, auditEntityRequest)).thenReturn(job);
        when(jobRepository.save(job)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.create(jobRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);

        verify(jobRepository).save(job);
        verify(jobRepository).refreshParentJobEstimatedHours(PARENT_ID_2);
        verify(jobRepository).refreshParentJobActualHours(PARENT_ID_2);
    }

    // ============ update() Tests ============

    @ParameterizedTest
    @NullSource
    void update_whenIdIsNull_shouldThrowValidationException(Integer id) {
        JobRequest jobRequest = generateJobRequest1();
        assertThrows(ValidationException.class, () -> jobService.update(id, jobRequest));
    }

    @ParameterizedTest
    @NullSource
    void update_whenJobRequestIsNull_shouldThrowValidationException(JobRequest jobRequest) {
        assertThrows(ValidationException.class, () -> jobService.update(ID_1, jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenNameIsNullOrBlank_shouldThrowValidationException(String name) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setName(name);

        assertThrows(ValidationException.class, () -> jobService.update(ID_1, jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenTypeCodeIsNullOrBlank_shouldThrowValidationException(String typeCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setTypeCode(typeCode);

        assertThrows(ValidationException.class, () -> jobService.update(ID_1, jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenPriorityCodeIsNullOrBlank_shouldThrowValidationException(String priorityCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setPriorityCode(priorityCode);

        assertThrows(ValidationException.class, () -> jobService.update(ID_1, jobRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenStatusCodeIsNullOrBlank_shouldThrowValidationException(String statusCode) {
        JobRequest jobRequest = generateJobRequest1();
        jobRequest.setStatusCode(statusCode);

        assertThrows(ValidationException.class, () -> jobService.update(ID_1, jobRequest));
    }

    @Test
    void update_whenNoJobExists_shouldThrowResourceNotFoundException() {
        JobRequest jobRequest = generateJobRequest2();
        when(jobRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> jobService.update(ID_UNKNOWN, jobRequest));
    }

    @Test
    void update_whenJobExists_shouldSaveAndReturnResponse() {
        JobRequest jobRequest = generateJobRequest1();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job existingJob = generateJob1();
        Job savedJob = generateJob1();

        JobResponse jobResponse = generateJobResponse1();
        UserResponse userResponse = generateUserResponse1();

        when(jobRepository.findById(ID_1)).thenReturn(of(existingJob));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(jobRepository.save(existingJob)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.update(ID_1, jobRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);
        verify(jobMapper).mapToJob(existingJob, jobRequest, auditEntityRequest);
        verify(jobRepository).save(existingJob);
    }

    @Test
    void update_whenJobHasParentId_shouldRefreshParentJobHours() {
        JobRequest jobRequest = generateJobRequest2();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job existingJob = generateJob2();
        Job savedJob = generateJob2();

        JobResponse jobResponse = generateJobResponse2();
        UserResponse userResponse = generateUserResponse2();

        when(jobRepository.findById(ID_2)).thenReturn(of(existingJob));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(jobRepository.save(existingJob)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.update(ID_2, jobRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);
        verify(jobRepository).refreshParentJobEstimatedHours(PARENT_ID_2);
        verify(jobRepository).refreshParentJobActualHours(PARENT_ID_2);
    }

    @Test
    void update_whenJobHasNoParentId_shouldNotRefreshParentJobHours() {
        JobRequest jobRequest = generateJobRequest1();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job existingJob = generateJob1();
        Job savedJob = generateJob1();

        JobResponse jobResponse = generateJobResponse1();
        UserResponse userResponse = generateUserResponse1();

        when(jobRepository.findById(ID_1)).thenReturn(of(existingJob));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        doNothing().when(jobMapper)
                   .mapToJob(existingJob, jobRequest, auditEntityRequest);
        when(jobRepository.save(existingJob)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.update(ID_1, jobRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);
        verify(jobRepository, never()).refreshParentJobEstimatedHours(PARENT_ID_1_NULL);
        verify(jobRepository, never()).refreshParentJobActualHours(PARENT_ID_1_NULL);
    }

    // ============ updateStatus() Tests ============

    @ParameterizedTest
    @NullSource
    void updateStatus_whenIdIsNull_shouldThrowValidationException(Integer id) {
        JobStatusUpdateRequest jobStatusUpdateRequest = generateJobStatusUpdateRequest(STATUS_CODE_1);
        assertThrows(ValidationException.class, () -> jobService.updateStatus(id, jobStatusUpdateRequest));
    }

    @ParameterizedTest
    @NullSource
    void updateStatus_whenJobStatusUpdateRequestIsNull_shouldThrowValidationException(JobStatusUpdateRequest jobStatusUpdateRequest) {
        assertThrows(ValidationException.class, () -> jobService.updateStatus(ID_1, jobStatusUpdateRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void updateStatus_whenStatusCodeIsNullOrBlank_shouldThrowValidationException(String statusCode) {
        JobStatusUpdateRequest jobStatusUpdateRequest = generateJobStatusUpdateRequest(statusCode);
        assertThrows(ValidationException.class, () -> jobService.updateStatus(ID_1, jobStatusUpdateRequest));
    }

    @Test
    void updateStatus_whenNoJobExists_shouldThrowResourceNotFoundException() {
        JobStatusUpdateRequest jobStatusUpdateRequest = generateJobStatusUpdateRequest(STATUS_CODE_1);
        when(jobRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> jobService.updateStatus(ID_UNKNOWN, jobStatusUpdateRequest));
    }

    @Test
    void updateStatus_whenJobExists_shouldUpdateStatus() {
        JobStatusUpdateRequest jobStatusUpdateRequest = generateJobStatusUpdateRequest(STATUS_CODE_1);
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Job existingJob = generateJob1();
        Job savedJob = generateJob1();

        JobResponse jobResponse = generateJobResponse1();
        UserResponse userResponse = generateUserResponse1();

        when(jobRepository.findById(ID_1)).thenReturn(of(existingJob));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(jobRepository.save(existingJob)).thenReturn(savedJob);
        when(userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo())).thenReturn(userResponse);
        when(jobMapper.toJobResponse(savedJob, userResponse)).thenReturn(jobResponse);

        JobResponse result = jobService.updateStatus(ID_1, jobStatusUpdateRequest);

        assertNotNull(result);
        assertEquals(jobResponse, result);
        verify(jobMapper).updateJobStatus(existingJob, jobStatusUpdateRequest.getStatusCode(), auditEntityRequest);
        verify(jobRepository).save(existingJob);
    }

    // ============ delete() Tests ============

    @ParameterizedTest
    @NullSource
    void delete_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> jobService.delete(id));
    }

    @Test
    void delete_whenJobExists_shouldDeleteAndRefreshParentHours() {
        Job job = generateJob2();

        when(jobRepository.findById(ID_2)).thenReturn(Optional.of(job));
        jobService.delete(ID_2);

        verify(jobRepository).delete(job);
        verify(jobRepository).refreshParentJobEstimatedHours(PARENT_ID_2);
        verify(jobRepository).refreshParentJobActualHours(PARENT_ID_2);
    }

    @Test
    void delete_whenNoJobExists_shouldThrowResourceNotFoundException() {
        when(jobRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> jobService.delete(ID_UNKNOWN));
    }

    // ============ refreshJobActualHours() Tests ============

    @ParameterizedTest
    @NullSource
    void refreshJobActualHours_whenJobIdIsNull_shouldDoNothing(Integer jobId) {
        jobService.refreshJobActualHours(jobId);
        verifyNoInteractions(jobRepository);
    }

    @Test
    void refreshJobActualHours_whenJobIdIsNotNull_shouldRefreshHours() {
        Job job = generateJob2();

        when(jobRepository.findById(ID_2)).thenReturn(Optional.of(job));
        jobService.refreshJobActualHours(ID_2);

        verify(jobRepository).refreshJobActualHours(ID_2);
        verify(jobRepository).refreshParentJobActualHours(PARENT_ID_2);
    }

    // ============ Test data Creation Methods ============

    private Job generateJob1() {
        return Job.builder()
                  .id(ID_1)
                  .sprintId(SPRINT_ID_1)
                  .name(NAME_1)
                  .description(DESCRIPTION_1)
                  .typeCode(TYPE_CODE_1)
                  .priorityCode(PRIORITY_CODE_1)
                  .statusCode(STATUS_CODE_1)
                  .assignedTo(ASSIGNED_TO_1)
                  .estimatedHours(ESTIMATED_HOURS_1)
                  .actualHours(ACTUAL_HOURS_1)
                  .parentId(PARENT_ID_1_NULL)
                  .createdAt(CREATED_AT_1)
                  .createdBy(CREATED_BY_1)
                  .updatedAt(UPDATED_AT_1)
                  .updatedBy(UPDATED_BY_1)
                  .build();
    }

    private Job generateJob2() {
        return Job.builder()
                  .id(ID_2)
                  .sprintId(SPRINT_ID_2)
                  .name(NAME_2)
                  .description(DESCRIPTION_2)
                  .typeCode(TYPE_CODE_2)
                  .priorityCode(PRIORITY_CODE_2)
                  .statusCode(STATUS_CODE_2)
                  .assignedTo(ASSIGNED_TO_2)
                  .estimatedHours(ESTIMATED_HOURS_2)
                  .actualHours(ACTUAL_HOURS_2)
                  .parentId(PARENT_ID_2)
                  .createdAt(CREATED_AT_2)
                  .createdBy(CREATED_BY_2)
                  .build();
    }

    private JobRequest generateJobRequest1() {
        return JobRequest.builder()
                         .sprintId(SPRINT_ID_1)
                         .name(NAME_1)
                         .description(DESCRIPTION_1)
                         .typeCode(TYPE_CODE_1)
                         .priorityCode(PRIORITY_CODE_1)
                         .statusCode(STATUS_CODE_1)
                         .assignedTo(ASSIGNED_TO_1)
                         .estimatedHours(ESTIMATED_HOURS_1)
                         .actualHours(ACTUAL_HOURS_1)
                         .parentId(PARENT_ID_1_NULL)
                         .build();
    }

    private JobRequest generateJobRequest2() {
        return JobRequest.builder()
                         .sprintId(SPRINT_ID_2)
                         .name(NAME_2)
                         .description(DESCRIPTION_2)
                         .typeCode(TYPE_CODE_2)
                         .priorityCode(PRIORITY_CODE_2)
                         .statusCode(STATUS_CODE_2)
                         .assignedTo(ASSIGNED_TO_2)
                         .estimatedHours(ESTIMATED_HOURS_2)
                         .actualHours(ACTUAL_HOURS_2)
                         .parentId(PARENT_ID_2)
                         .build();
    }

    private JobStatusUpdateRequest generateJobStatusUpdateRequest(String statusCode) {
        JobStatusUpdateRequest request = new JobStatusUpdateRequest();
        request.setStatusCode(statusCode);
        return request;
    }

    private AuditEntityRequest generateAuditEntityRequest() {
        return AuditEntityRequest.builder()
                                 .createdBy(CREATED_BY_1)
                                 .createdAt(CREATED_AT_1)
                                 .updatedBy(UPDATED_BY_1)
                                 .updatedAt(UPDATED_AT_1)
                                 .build();
    }

    private JobResponse generateJobResponse1() {
        return JobResponse.builder()
                          .id(ID_1)
                          .sprintId(SPRINT_ID_1)
                          .name(NAME_1)
                          .description(DESCRIPTION_1)
                          .typeCode(TYPE_CODE_1)
                          .priorityCode(PRIORITY_CODE_1)
                          .statusCode(STATUS_CODE_1)
                          .assignedTo(ASSIGNED_TO_1)
                          .estimatedHours(ESTIMATED_HOURS_1)
                          .actualHours(ACTUAL_HOURS_1)
                          .parentId(PARENT_ID_1_NULL)
                          .createdAt(CREATED_AT_1)
                          .createdBy(CREATED_BY_1)
                          .updatedAt(UPDATED_AT_1)
                          .updatedBy(UPDATED_BY_1)
                          .build();
    }

    private JobResponse generateJobResponse2() {
        return JobResponse.builder()
                          .id(ID_2)
                          .sprintId(SPRINT_ID_2)
                          .name(NAME_2)
                          .description(DESCRIPTION_2)
                          .typeCode(TYPE_CODE_2)
                          .priorityCode(PRIORITY_CODE_2)
                          .statusCode(STATUS_CODE_2)
                          .assignedTo(ASSIGNED_TO_2)
                          .estimatedHours(ESTIMATED_HOURS_2)
                          .actualHours(ACTUAL_HOURS_2)
                          .parentId(PARENT_ID_2)
                          .createdAt(CREATED_AT_2)
                          .createdBy(CREATED_BY_2)
                          .build();
    }

    private UserResponse generateUserResponse1() {
        return UserResponse.builder()
                           .id(ASSIGNED_TO_1)
                           .build();
    }

    private UserResponse generateUserResponse2() {
        return UserResponse.builder()
                           .id(ASSIGNED_TO_2)
                           .build();
    }
}