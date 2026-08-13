package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.TimesheetRequest;
import com.kan.jtrack.dto.response.TimesheetResponse;
import com.kan.jtrack.entity.Timesheet;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TimesheetMapper {

    TimesheetResponse toTimesheetResponse(Timesheet timesheet);

    @Mapping(target = "createdAt", source = "auditEntityRequest.createdAt")
    @Mapping(target = "createdBy", source = "auditEntityRequest.createdBy")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Timesheet toTimesheet(TimesheetRequest timesheetRequest, AuditEntityRequest auditEntityRequest);

    @Mapping(target = "updatedAt", source = "auditEntityRequest.updatedAt")
    @Mapping(target = "updatedBy", source = "auditEntityRequest.updatedBy")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToTimesheet(@MappingTarget Timesheet timesheet, TimesheetRequest timesheetRequest, AuditEntityRequest auditEntityRequest);
}
