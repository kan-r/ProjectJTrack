package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobPriorityResponse;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.JobPriorityMapper;
import com.kan.jtrack.repository.JobPriorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class JobPriorityService {

    private final JobPriorityRepository jobPriorityRepository;
    private final JobPriorityMapper jobPriorityMapper;


    public JobPriorityService(JobPriorityRepository jobPriorityRepository, JobPriorityMapper jobPriorityMapper) {
        this.jobPriorityRepository = jobPriorityRepository;
        this.jobPriorityMapper = jobPriorityMapper;
    }

    @Transactional(readOnly = true)
    public List<JobPriorityResponse> getAll() {
        log.debug("getAll()");

        return jobPriorityRepository.findAll()
                                    .stream()
                                    .map(jobPriorityMapper::toJobPriorityResponse)
                                    .sorted(Comparator.comparingInt(JobPriorityResponse::getDisplayOrder))
                                    .toList();
    }

    @Transactional(readOnly = true)
    public JobPriorityResponse getById(String id) {
        log.debug("getById({})", id);

        validateStringNotNullOrBlank(id, "JobPriority id/code");

        return jobPriorityRepository.findById(id.toUpperCase())
                                    .map(jobPriorityMapper::toJobPriorityResponse)
                                    .orElseThrow(() -> new ResourceNotFoundException("JobPriority not found for id/code: " + id));
    }
}
