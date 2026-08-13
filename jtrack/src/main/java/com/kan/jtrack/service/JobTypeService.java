package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobTypeResponse;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.JobTypeMapper;
import com.kan.jtrack.repository.JobTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class JobTypeService {

    private final JobTypeRepository jobTypeRepository;
    private final JobTypeMapper jobTypeMapper;


    public JobTypeService(JobTypeRepository jobTypeRepository, JobTypeMapper jobTypeMapper) {
        this.jobTypeRepository = jobTypeRepository;
        this.jobTypeMapper = jobTypeMapper;
    }

    @Transactional(readOnly = true)
    public List<JobTypeResponse> getAll() {
        log.debug("getAll()");

        return jobTypeRepository.findAll()
                                .stream()
                                .map(jobTypeMapper::toJobTypeResponse)
                                .toList();
    }

    @Transactional(readOnly = true)
    public JobTypeResponse getById(String id) {
        log.debug("getById({})", id);

        validateStringNotNullOrBlank(id, "JobType id/code");

        return jobTypeRepository.findById(id.toUpperCase())
                                .map(jobTypeMapper::toJobTypeResponse)
                                .orElseThrow(() -> new ResourceNotFoundException("JobType not found for id/code: " + id));
    }
}