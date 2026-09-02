package com.kan.jtrack.controller;

import com.kan.jtrack.dto.request.SprintRequest;
import com.kan.jtrack.dto.response.SprintResponse;
import com.kan.jtrack.service.SprintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sprints")
public class SprintController {

    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping
    public List<SprintResponse> getAll() {
        log.info("getAll()");
        return sprintService.getAll();
    }

    @GetMapping("/{id}")
    public SprintResponse getById(@PathVariable Integer id) {
        log.debug("getById({})", id);
        return sprintService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public SprintResponse create(@RequestBody SprintRequest sprintRequest) {
        log.debug("create({})", sprintRequest);
        return sprintService.create(sprintRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public SprintResponse update(@PathVariable Integer id, @RequestBody SprintRequest sprintRequest) {
        log.debug("update({}, {})", id, sprintRequest);
        return sprintService.update(id, sprintRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public void delete(@PathVariable Integer id) {
        log.debug("delete({})", id);
        sprintService.delete(id);
    }
}
