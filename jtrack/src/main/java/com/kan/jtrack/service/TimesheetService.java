package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.TimesheetRequest;
import com.kan.jtrack.dto.response.TimesheetResponse;
import com.kan.jtrack.entity.Timesheet;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.mapper.TimesheetMapper;
import com.kan.jtrack.repository.TimesheetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.*;

@Slf4j
@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final TimesheetMapper timesheetMapper;
    private final AuditEntityService auditEntityService;
    private final JobService jobService;


    public TimesheetService(TimesheetRepository timesheetRepository,
                            TimesheetMapper timesheetMapper,
                            AuditEntityService auditEntityService,
                            JobService jobService) {
        this.timesheetRepository = timesheetRepository;
        this.timesheetMapper = timesheetMapper;
        this.auditEntityService = auditEntityService;
        this.jobService = jobService;
    }

    @Transactional(readOnly = true)
    public List<TimesheetResponse> getAll() {
        log.debug("getAll()");

        return timesheetRepository.findAll()
                                  .stream()
                                  .map(timesheetMapper::toTimesheetResponse)
                                  .toList();
    }

    @Transactional(readOnly = true)
    public TimesheetResponse getById(Integer id) {
        log.debug("getById({})", id);
        return timesheetMapper.toTimesheetResponse(getByIdOrThrow(id));
    }

    @Transactional
    public TimesheetResponse create(TimesheetRequest timesheetRequest) {
        log.debug("create({})", timesheetRequest);

        validateCreateTimesheetRequest(timesheetRequest);

        Timesheet timesheet = timesheetMapper.toTimesheet(timesheetRequest, auditEntityService.generateAuditEntityRequest());
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);
        jobService.refreshJobActualHours(savedTimesheet.getJobId());

        return timesheetMapper.toTimesheetResponse(savedTimesheet);
    }

    @Transactional
    public TimesheetResponse update(Integer id, TimesheetRequest timesheetRequest) {
        log.debug("update({}, {})", id, timesheetRequest);

        validateUpdateTimesheetRequest(timesheetRequest);

        Timesheet timesheet = getByIdOrThrow(id);
        timesheetMapper.mapToTimesheet(timesheet, timesheetRequest, auditEntityService.generateAuditEntityRequest());
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        jobService.refreshJobActualHours(savedTimesheet.getJobId());

        return timesheetMapper.toTimesheetResponse(savedTimesheet);
    }

    @Transactional
    public void delete(Integer id) {
        log.debug("delete({})", id);

        Timesheet timesheet = getByIdOrThrow(id);
        timesheetRepository.delete(timesheet);
        jobService.refreshJobActualHours(timesheet.getJobId());
    }

    private Timesheet getByIdOrThrow(Integer id) {
        validateObjectNotNull(id, "Timesheet id");
        return timesheetRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found for id: " + id));
    }

    public void validateCreateTimesheetRequest(TimesheetRequest timesheetRequest) {
        validateObjectNotNull(timesheetRequest, "Timesheet Request");
        validateStringNotNullOrBlank(timesheetRequest.getUserId(), "Timesheet userId");
        validateObjectNotNull(timesheetRequest.getJobId(), "Timesheet jobId");
    }

    public void validateUpdateTimesheetRequest(TimesheetRequest timesheetRequest) {
        validateObjectNotNull(timesheetRequest, "Timesheet Request");
        validateStringNotBlank(timesheetRequest.getUserId(), "Timesheet userId");
    }
}
