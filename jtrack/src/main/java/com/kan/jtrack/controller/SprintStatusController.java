package com.kan.jtrack.controller;

import com.kan.jtrack.dto.response.SprintStatusResponse;
import com.kan.jtrack.service.SprintStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sprintStatuses")
public class SprintStatusController {

    private final SprintStatusService sprintStatusService;

    public SprintStatusController(SprintStatusService sprintStatusService) {
        this.sprintStatusService = sprintStatusService;
    }

    @GetMapping
    public List<SprintStatusResponse> getAll() {
        log.info("getAll()");
        return sprintStatusService.getAll();
    }

    @GetMapping("/{id}")
    public SprintStatusResponse getById(@PathVariable String id) {
        log.debug("getById({})", id);
        return sprintStatusService.getById(id);
    }
}
