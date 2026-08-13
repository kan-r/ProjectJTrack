package com.kan.jtrack.controller;

import com.kan.jtrack.dto.response.JobTypeResponse;
import com.kan.jtrack.service.JobTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jobTypes")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    public JobTypeController(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    @GetMapping
    public List<JobTypeResponse> getAll() {
        log.info("getAll()");
        return jobTypeService.getAll();
    }

    @GetMapping("/{id}")
    public JobTypeResponse getById(@PathVariable String id) {
        log.debug("getById({})", id);
        return jobTypeService.getById(id);
    }
}
