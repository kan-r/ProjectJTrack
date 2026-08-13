package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.TimesheetRequest;
import com.kan.jtrack.dto.response.TimesheetResponse;
import com.kan.jtrack.entity.Timesheet;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.TimesheetMapper;
import com.kan.jtrack.repository.TimesheetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimesheetServiceTest {

    private static final Integer ID_1 = 1;
    private static final String USER_ID_1 = "user-1";
    private static final Integer JOB_ID_1 = 1;
    private static final LocalDate WORKED_DATE_1 = LocalDate.of(2026, 8, 9);
    private static final Double WORKED_HOURS_1 = 4.5;
    private static final LocalDateTime CREATED_AT_1 = LocalDateTime.of(2026, 8, 9, 16, 0);
    private static final String CREATED_BY_1 = "user-1";
    private static final LocalDateTime UPDATED_AT_1 = LocalDateTime.of(2026, 8, 13, 16, 0);
    private static final String UPDATED_BY_1 = "user-1";

    private static final Integer ID_2 = 2;
    private static final String USER_ID_2 = "user-2";
    private static final Integer JOB_ID_2 = 2;
    private static final LocalDate WORKED_DATE_2 = LocalDate.of(2026, 8, 10);
    private static final Double WORKED_HOURS_2 = 5.0;
    private static final LocalDateTime CREATED_AT_2 = LocalDateTime.of(2026, 8, 10, 16, 0);
    private static final String CREATED_BY_2 = "user-2";

    private static final Integer ID_UNKNOWN = 999;

    @Mock
    private TimesheetRepository timesheetRepository;

    @Mock
    private TimesheetMapper timesheetMapper;

    @Mock
    private AuditEntityService auditEntityService;

    @Mock
    private JobService jobService;

    @InjectMocks
    private TimesheetService timesheetService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenTimesheetsExist_shouldReturnTimesheetResponseList() {
        Timesheet timesheet1 = generateTimesheet1();
        Timesheet timesheet2 = generateTimesheet2();

        TimesheetResponse timesheetResponse1 = generateTimesheetResponse1();
        TimesheetResponse timesheetResponse2 = generateTimesheetResponse2();

        when(timesheetRepository.findAll()).thenReturn(List.of(timesheet1, timesheet2));
        when(timesheetMapper.toTimesheetResponse(timesheet1)).thenReturn(timesheetResponse1);
        when(timesheetMapper.toTimesheetResponse(timesheet2)).thenReturn(timesheetResponse2);

        List<TimesheetResponse> result = timesheetService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(timesheetResponse1));
        assertTrue(result.contains(timesheetResponse2));
    }

    @Test
    void getAll_whenNoTimesheetsExist_shouldReturnEmptyList() {
        when(timesheetRepository.findAll()).thenReturn(List.of());

        List<TimesheetResponse> result = timesheetService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> timesheetService.getById(id));
    }

    @Test
    void getById_whenTimesheetExists_shouldReturnTimesheetResponse() {
        Timesheet timesheet = generateTimesheet1();
        TimesheetResponse timesheetResponse = generateTimesheetResponse1();

        when(timesheetRepository.findById(ID_1)).thenReturn(of(timesheet));
        when(timesheetMapper.toTimesheetResponse(timesheet)).thenReturn(timesheetResponse);

        TimesheetResponse result = timesheetService.getById(ID_1);

        assertNotNull(result);
        assertEquals(timesheetResponse, result);
    }

    @Test
    void getById_whenNoTimesheetExists_shouldThrowResourceNotFoundException() {
        when(timesheetRepository.findById(ID_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> timesheetService.getById(ID_UNKNOWN));
    }

    // ============ create() Tests ============

    @ParameterizedTest
    @NullSource
    void create_whenTimesheetRequestIsNull_shouldThrowValidationException(TimesheetRequest timesheetRequest) {
        assertThrows(ValidationException.class, () -> timesheetService.create(timesheetRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenUserIdIsNullOrBlank_shouldThrowValidationException(String userId) {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        timesheetRequest.setUserId(userId);

        assertThrows(ValidationException.class, () -> timesheetService.create(timesheetRequest));
    }

    @ParameterizedTest
    @NullSource
    void create_whenJobIdIsNull_shouldThrowValidationException(Integer jobId) {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        timesheetRequest.setJobId(jobId);

        assertThrows(ValidationException.class, () -> timesheetService.create(timesheetRequest));
    }

    @Test
    void create_whenTimesheetRequestIsValid_shouldSaveAndReturnResponse() {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Timesheet timesheet = generateTimesheet1();
        timesheet.setId(null);

        TimesheetResponse timesheetResponse = generateTimesheetResponse1();

        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(timesheetMapper.toTimesheet(timesheetRequest, auditEntityRequest)).thenReturn(timesheet);
        when(timesheetRepository.save(timesheet)).thenReturn(timesheet);
        when(timesheetMapper.toTimesheetResponse(timesheet)).thenReturn(timesheetResponse);

        TimesheetResponse result = timesheetService.create(timesheetRequest);

        assertNotNull(result);
        assertEquals(timesheetResponse, result);

        verify(timesheetRepository).save(timesheet);
        verify(jobService).refreshJobActualHours(JOB_ID_1);
    }

    // ============ update() Tests ============

    @ParameterizedTest
    @NullSource
    void update_whenIdIsNull_shouldThrowValidationException(Integer id) {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        assertThrows(ValidationException.class, () -> timesheetService.update(id, timesheetRequest));
    }

    @ParameterizedTest
    @NullSource
    void update_whenTimesheetRequestIsNull_shouldThrowValidationException(TimesheetRequest timesheetRequest) {
        assertThrows(ValidationException.class, () -> timesheetService.update(ID_1, timesheetRequest));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void update_whenUserIdIsBlank_shouldThrowValidationException(String userId) {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        timesheetRequest.setUserId(userId);

        assertThrows(ValidationException.class, () -> timesheetService.update(ID_1, timesheetRequest));
    }

    @Test
    void update_whenNoTimesheetExists_shouldThrowResourceNotFoundException() {
        TimesheetRequest timesheetRequest = generateTimesheetRequest1();
        when(timesheetRepository.findById(ID_UNKNOWN)).thenReturn(empty());

        assertThrows(ResourceNotFoundException.class, () -> timesheetService.update(ID_UNKNOWN, timesheetRequest));
    }

    @Test
    void update_whenTimesheetExists_shouldUpdateAndReturnResponse() {
        TimesheetRequest timesheetRequest = generateTimesheetRequest2();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Timesheet existingTimesheet = generateTimesheet2();
        Timesheet savedTimesheet = generateTimesheet2();

        TimesheetResponse timesheetResponse = generateTimesheetResponse2();

        when(timesheetRepository.findById(ID_2)).thenReturn(of(existingTimesheet));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        doNothing().when(timesheetMapper)
                   .mapToTimesheet(existingTimesheet, timesheetRequest, auditEntityRequest);
        when(timesheetRepository.save(existingTimesheet)).thenReturn(savedTimesheet);
        when(timesheetMapper.toTimesheetResponse(savedTimesheet)).thenReturn(timesheetResponse);

        TimesheetResponse result = timesheetService.update(ID_2, timesheetRequest);

        assertEquals(timesheetResponse, result);

        verify(timesheetRepository).save(existingTimesheet);
        verify(jobService).refreshJobActualHours(JOB_ID_2);
    }

    // ============ delete() Tests ============

    @ParameterizedTest
    @NullSource
    void delete_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> timesheetService.delete(id));
    }

    @Test
    void delete_whenTimesheetExists_shouldDeleteTimesheet() {
        Timesheet existingTimesheet = generateTimesheet1();

        when(timesheetRepository.findById(ID_1)).thenReturn(of(existingTimesheet));
        timesheetService.delete(ID_1);

        verify(timesheetRepository).delete(existingTimesheet);
        verify(jobService).refreshJobActualHours(JOB_ID_1);
    }

    @Test
    void delete_whenNoTimesheetExists_shouldThrowResourceNotFoundException() {
        when(timesheetRepository.findById(ID_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> timesheetService.delete(ID_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private Timesheet generateTimesheet1() {
        return Timesheet.builder()
                        .id(ID_1)
                        .userId(USER_ID_1)
                        .jobId(JOB_ID_1)
                        .workedDate(WORKED_DATE_1)
                        .workedHours(WORKED_HOURS_1)
                        .createdAt(CREATED_AT_1)
                        .createdBy(CREATED_BY_1)
                        .build();
    }

    private Timesheet generateTimesheet2() {
        return Timesheet.builder()
                        .id(ID_2)
                        .userId(USER_ID_2)
                        .jobId(JOB_ID_2)
                        .workedDate(WORKED_DATE_2)
                        .workedHours(WORKED_HOURS_2)
                        .createdAt(CREATED_AT_2)
                        .createdBy(CREATED_BY_2)
                        .build();
    }

    private TimesheetRequest generateTimesheetRequest1() {
        return TimesheetRequest.builder()
                               .userId(USER_ID_1)
                               .jobId(JOB_ID_1)
                               .workedDate(WORKED_DATE_1)
                               .workedHours(WORKED_HOURS_1)
                               .build();
    }

    private TimesheetRequest generateTimesheetRequest2() {
        return TimesheetRequest.builder()
                               .userId(USER_ID_2)
                               .jobId(JOB_ID_2)
                               .workedDate(WORKED_DATE_2)
                               .workedHours(WORKED_HOURS_2)
                               .build();
    }

    private AuditEntityRequest generateAuditEntityRequest() {
        return AuditEntityRequest.builder()
                                 .createdBy(CREATED_BY_1)
                                 .createdAt(CREATED_AT_1)
                                 .updatedBy(UPDATED_BY_1)
                                 .updatedAt(UPDATED_AT_1)
                                 .build();
    }

    private TimesheetResponse generateTimesheetResponse1() {
        return TimesheetResponse.builder()
                                .id(ID_1)
                                .userId(USER_ID_1)
                                .jobId(JOB_ID_1)
                                .workedDate(WORKED_DATE_1)
                                .workedHours(WORKED_HOURS_1)
                                .createdAt(CREATED_AT_1)
                                .createdBy(CREATED_BY_1)
                                .build();
    }

    private TimesheetResponse generateTimesheetResponse2() {
        return TimesheetResponse.builder()
                                .id(ID_2)
                                .userId(USER_ID_2)
                                .jobId(JOB_ID_2)
                                .workedDate(WORKED_DATE_2)
                                .workedHours(WORKED_HOURS_2)
                                .createdAt(CREATED_AT_2)
                                .createdBy(CREATED_BY_2)
                                .build();
    }
}