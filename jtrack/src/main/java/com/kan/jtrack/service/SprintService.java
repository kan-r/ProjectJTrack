package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.SprintRequest;
import com.kan.jtrack.dto.response.SprintResponse;
import com.kan.jtrack.entity.Sprint;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.SprintMapper;
import com.kan.jtrack.repository.SprintRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.*;

@Slf4j
@Service
public class SprintService {

    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;
    private final AuditEntityService auditEntityService;


    public SprintService(SprintRepository sprintRepository,
                         SprintMapper sprintMapper,
                         AuditEntityService auditEntityService) {
        this.sprintRepository = sprintRepository;
        this.sprintMapper = sprintMapper;
        this.auditEntityService = auditEntityService;
    }

    @Transactional(readOnly = true)
    public List<SprintResponse> getAll() {
        log.debug("getAll()");

        return sprintRepository.findAll()
                               .stream()
                               .map(sprintMapper::toSprintResponse)
                               .toList();
    }

    @Transactional(readOnly = true)
    public SprintResponse getById(Integer id) {
        log.debug("getById({})", id);
        return sprintMapper.toSprintResponse(getByIdOrThrow(id));
    }

    @Transactional
    public SprintResponse create(SprintRequest sprintRequest) {
        log.debug("create({})", sprintRequest);

        validateSprintRequest(sprintRequest);

        Sprint sprint = sprintMapper.toSprint(sprintRequest, auditEntityService.generateAuditEntityRequest());
        return sprintMapper.toSprintResponse(sprintRepository.save(sprint));
    }

    @Transactional
    public SprintResponse update(Integer id, SprintRequest sprintRequest) {
        log.debug("update({}, {})", id, sprintRequest);

        validateSprintRequest(sprintRequest);

        Sprint existingSprint = getByIdOrThrow(id);
        sprintMapper.mapToSprint(existingSprint, sprintRequest, auditEntityService.generateAuditEntityRequest());
        return sprintMapper.toSprintResponse(sprintRepository.save(existingSprint));
    }

    @Transactional
    public void delete(Integer id) {
        log.debug("delete({})", id);
        Sprint existingSprint = getByIdOrThrow(id);
        sprintRepository.delete(existingSprint);
    }

    private Sprint getByIdOrThrow(Integer id) {
        validateObjectNotNull(id, "Sprint id");
        return sprintRepository.findById(id)
                               .orElseThrow(() -> new ResourceNotFoundException("Sprint not found for id: " + id));
    }

    public void validateSprintRequest(SprintRequest sprintRequest) {
        validateObjectNotNull(sprintRequest, "Sprint Request");
        validateStringNotNullOrBlank(sprintRequest.getName(), "Sprint name");
        validateStringNotNullOrBlank(sprintRequest.getStatusCode(), "Sprint statusCode");
    }
}
