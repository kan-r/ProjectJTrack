package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.request.JobStatusUpdateRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.entity.Job;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.JobMapper;
import com.kan.jtrack.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.*;

@Slf4j
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final UserService userService;
    private final AuditEntityService auditEntityService;


    public JobService(JobRepository jobRepository,
                      JobMapper jobMapper,
                      UserService userService,
                      AuditEntityService auditEntityService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.userService = userService;
        this.auditEntityService = auditEntityService;
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAll() {
        log.debug("getAll()");

        return jobRepository.findAll()
                            .stream()
                            .map(job -> jobMapper.toJobResponse(job, userService.getUserByIdIgnoreBlank(job.getAssignedTo())))
                            .toList();
    }

    @Transactional(readOnly = true)
    public JobResponse getById(Integer id) {
        log.debug("getById({})", id);
        Job job = getByIdOrThrow(id);
        return jobMapper.toJobResponse(job, userService.getUserByIdIgnoreBlank(job.getAssignedTo()));
    }

    @Transactional
    public JobResponse create(JobRequest jobRequest) {
        log.debug("create({})", jobRequest);

        validateJobRequest(jobRequest);

        Job job = jobMapper.toJob(jobRequest, auditEntityService.generateAuditEntityRequest());
        Job savedJob = jobRepository.save(job);
        refreshParentJobHours(savedJob.getParentId());

        return jobMapper.toJobResponse(savedJob, userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo()));
    }

    @Transactional
    public JobResponse update(Integer id, JobRequest jobRequest) {
        log.debug("update({}, {})", id, jobRequest);

        validateJobRequest(jobRequest);

        Job job = getByIdOrThrow(id);
        jobMapper.mapToJob(job, jobRequest, auditEntityService.generateAuditEntityRequest());
        Job savedJob = jobRepository.save(job);
        refreshParentJobHours(savedJob.getParentId());

        return jobMapper.toJobResponse(savedJob, userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo()));
    }

    @Transactional
    public JobResponse updateStatus(Integer id, JobStatusUpdateRequest jobStatusUpdateRequest) {
        log.debug("updateStatus({}, {})", id, jobStatusUpdateRequest);

        validateObjectNotNull(jobStatusUpdateRequest, "Job Status Update Request");
        validateStringNotNullOrBlank(jobStatusUpdateRequest.getStatusCode(), "Job statusCode");

        Job job = getByIdOrThrow(id);
        jobMapper.updateJobStatus(job, jobStatusUpdateRequest.getStatusCode(), auditEntityService.generateAuditEntityRequest());
        Job savedJob = jobRepository.save(job);

        return jobMapper.toJobResponse(savedJob, userService.getUserByIdIgnoreBlank(savedJob.getAssignedTo()));
    }

    @Transactional
    public void delete(Integer id) {
        log.debug("delete({})", id);

        Job job = getByIdOrThrow(id);
        jobRepository.delete(job);
        refreshParentJobHours(job.getParentId());
    }

    public void refreshJobActualHours(Integer jobId) {
        if (jobId == null) {
            return;
        }
        jobRepository.refreshJobActualHours(jobId);
        jobRepository.findById(jobId)
                     .ifPresent(job -> refreshParentJobActualHours(job.getParentId()));
    }

    private void refreshParentJobActualHours(Integer parentId) {
        if (parentId == null) {
            return;
        }
        jobRepository.refreshParentJobActualHours(parentId);
    }

    private void refreshParentJobHours(Integer parentId) {
        if (parentId == null) {
            return;
        }
        jobRepository.refreshParentJobEstimatedHours(parentId);
        jobRepository.refreshParentJobActualHours(parentId);
    }

    private Job getByIdOrThrow(Integer id) {
        validateObjectNotNull(id, "Job id");
        return jobRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Job not found for id: " + id));
    }

    private void validateJobRequest(JobRequest jobRequest) {
        validateObjectNotNull(jobRequest, "Job Request");
        validateStringNotNullOrBlank(jobRequest.getName(), "Job name");
        validateStringNotNullOrBlank(jobRequest.getTypeCode(), "Job typeCode");
        validateStringNotNullOrBlank(jobRequest.getPriorityCode(), "Job priorityCode");
        validateStringNotNullOrBlank(jobRequest.getStatusCode(), "Job statusCode");
    }
}
