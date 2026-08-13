package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobStatusResponse;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.JobStatusMapper;
import com.kan.jtrack.repository.JobStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class JobStatusService {

    private final JobStatusRepository jobStatusRepository;
    private final JobStatusMapper jobStatusMapper;


    public JobStatusService(JobStatusRepository jobStatusRepository, JobStatusMapper jobStatusMapper) {
        this.jobStatusRepository = jobStatusRepository;
        this.jobStatusMapper = jobStatusMapper;
    }

    @Transactional(readOnly = true)
    public List<JobStatusResponse> getAll() {
        log.debug("getAll()");

        return jobStatusRepository.findAll()
                                  .stream()
                                  .map(jobStatusMapper::toJobStatusResponse)
                                  .sorted(Comparator.comparingInt(JobStatusResponse::getDisplayOrder))
                                  .toList();
    }

    @Transactional(readOnly = true)
    public JobStatusResponse getById(String id) {
        log.debug("getById({})", id);

        validateStringNotNullOrBlank(id, "JobStatus id/code");

        return jobStatusRepository.findById(id.toUpperCase())
                                  .map(jobStatusMapper::toJobStatusResponse)
                                  .orElseThrow(() -> new ResourceNotFoundException("JobStatus not found for id/code: " + id));
    }
}