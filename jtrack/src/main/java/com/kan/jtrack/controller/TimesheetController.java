package com.kan.jtrack.controller;

import com.kan.jtrack.dto.request.TimesheetRequest;
import com.kan.jtrack.dto.response.TimesheetResponse;
import com.kan.jtrack.service.TimesheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping
    public List<TimesheetResponse> getAll() {
        log.info("getAll()");
        return timesheetService.getAll();
    }

    @GetMapping("/{id}")
    public TimesheetResponse getById(@PathVariable Integer id) {
        log.debug("getById({})", id);
        return timesheetService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimesheetResponse create(@RequestBody TimesheetRequest timesheetRequest) {
        log.debug("create({})", timesheetRequest);
        return timesheetService.create(timesheetRequest);
    }

    @PutMapping("/{id}")
    public TimesheetResponse update(@PathVariable Integer id, @RequestBody TimesheetRequest timesheetRequest) {
        log.debug("update({}, {})", id, timesheetRequest);
        return timesheetService.update(id, timesheetRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.debug("delete({})", id);
        timesheetService.delete(id);
    }
}
