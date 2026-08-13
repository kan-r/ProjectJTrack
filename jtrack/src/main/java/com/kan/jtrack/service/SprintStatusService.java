package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.SprintStatusResponse;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.SprintStatusMapper;
import com.kan.jtrack.repository.SprintStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class SprintStatusService {

    private final SprintStatusRepository sprintStatusRepository;
    private final SprintStatusMapper sprintStatusMapper;


    public SprintStatusService(SprintStatusRepository sprintStatusRepository, SprintStatusMapper sprintStatusMapper) {
        this.sprintStatusRepository = sprintStatusRepository;
        this.sprintStatusMapper = sprintStatusMapper;
    }

    @Transactional(readOnly = true)
    public List<SprintStatusResponse> getAll() {
        log.debug("getAll()");

        return sprintStatusRepository.findAll()
                                     .stream()
                                     .map(sprintStatusMapper::toSprintStatusResponse)
                                     .sorted(Comparator.comparingInt(SprintStatusResponse::getDisplayOrder))
                                     .toList();
    }

    @Transactional(readOnly = true)
    public SprintStatusResponse getById(String id) {
        log.debug("getById({})", id);

        validateStringNotNullOrBlank(id, "JobStatus id/code");

        return sprintStatusRepository.findById(id.toUpperCase())
                                     .map(sprintStatusMapper::toSprintStatusResponse)
                                     .orElseThrow(() -> new ResourceNotFoundException("SprintStatus not found for id/code: " + id));
    }
}