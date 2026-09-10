package com.kan.jtrack.controller;

import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.request.JobStatusUpdateRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public JobResponse create(@RequestBody JobRequest jobRequest) {
        log.debug("create({})", jobRequest);
        return jobService.create(jobRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public JobResponse update(@PathVariable Integer id, @RequestBody JobRequest jobRequest) {
        log.debug("update({}, {})", id, jobRequest);
        return jobService.update(id, jobRequest);
    }

    @PatchMapping("/{id}/status")
    public JobResponse updateStatus(@PathVariable Integer id,
                                    @RequestBody JobStatusUpdateRequest jobStatusUpdateRequest) {
        log.debug("updateStatus({}, {})", id, jobStatusUpdateRequest);
        return jobService.updateStatus(id, jobStatusUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public void delete(@PathVariable Integer id) {
        log.debug("delete({})", id);
        jobService.delete(id);
    }
}
