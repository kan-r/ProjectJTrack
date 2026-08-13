package com.kan.jtrack.controller;

import com.kan.jtrack.dto.response.JobStatusResponse;
import com.kan.jtrack.service.JobStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jobStatuses")
public class JobStatusController {

    private final JobStatusService jobStatusService;

    public JobStatusController(JobStatusService jobStatusService) {
        this.jobStatusService = jobStatusService;
    }

    @GetMapping
    public List<JobStatusResponse> getAll() {
        log.info("getAll()");
        return jobStatusService.getAll();
    }

    @GetMapping("/{id}")
    public JobStatusResponse getById(@PathVariable String id) {
        log.debug("getById({})", id);
        return jobStatusService.getById(id);
    }
}
