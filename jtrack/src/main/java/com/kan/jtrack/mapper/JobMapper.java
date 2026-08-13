package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.entity.Job;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface JobMapper {

    JobResponse toJobResponse(Job job);

    @Mapping(target = "createdAt", source = "auditEntityRequest.createdAt")
    @Mapping(target = "createdBy", source = "auditEntityRequest.createdBy")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Job toJob(JobRequest jobRequest, AuditEntityRequest auditEntityRequest);

    @Mapping(target = "updatedAt", source = "auditEntityRequest.updatedAt")
    @Mapping(target = "updatedBy", source = "auditEntityRequest.updatedBy")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "sprintId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "assignedTo", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToJob(@MappingTarget Job job, JobRequest jobRequest, AuditEntityRequest auditEntityRequest);
}
