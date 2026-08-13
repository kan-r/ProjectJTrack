package com.kan.jtrack.controller;

import com.kan.jtrack.dto.response.JobPriorityResponse;
import com.kan.jtrack.service.JobPriorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jobPriorities")
public class JobPriorityController {

    private final JobPriorityService jobPriorityService;

    public JobPriorityController(JobPriorityService jobPriorityService) {
        this.jobPriorityService = jobPriorityService;
    }

    @GetMapping
    public List<JobPriorityResponse> getAll() {
        log.info("getAll()");
        return jobPriorityService.getAll();
    }

    @GetMapping("/{id}")
    public JobPriorityResponse getById(@PathVariable String id) {
        log.debug("getById({})", id);
        return jobPriorityService.getById(id);
    }
}
