package com.kan.jtrack.controller;

import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobResponse> getAll() {
        log.info("getAll()");
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    public JobResponse getById(@PathVariable Integer id) {
        log.debug("getById({})", id);
        return jobService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse create(@RequestBody JobRequest jobRequest) {
        log.debug("create({})", jobRequest);
        return jobService.create(jobRequest);
    }

    @PutMapping("/{id}")
    public JobResponse update(@PathVariable Integer id, @RequestBody JobRequest jobRequest) {
        log.debug("update({}, {})", id, jobRequest);
        return jobService.update(id, jobRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.debug("delete({})", id);
        jobService.delete(id);
    }
}
