package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.entity.Job;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface JobMapper {

    @Mapping(target = "id", source = "job.id")
    @Mapping(target = "sprintName", source = "job.sprint.name")
    @Mapping(target = "typeDescription", source = "job.type.description")
    @Mapping(target = "priorityDescription", source = "job.priority.description")
    @Mapping(target = "statusDescription", source = "job.status.description")
    @Mapping(target = "assignedToName", source = "user.fullName")
    @Mapping(target = "parentName", source = "job.parent.name")
    JobResponse toJobResponse(Job job, UserResponse user);

    @Mapping(target = "createdAt", source = "auditEntityRequest.createdAt")
    @Mapping(target = "createdBy", source = "auditEntityRequest.createdBy")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Job toJob(JobRequest jobRequest, AuditEntityRequest auditEntityRequest);

    @Mapping(target = "updatedAt", source = "auditEntityRequest.updatedAt")
    @Mapping(target = "updatedBy", source = "auditEntityRequest.updatedBy")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void mapToJob(@MappingTarget Job job, JobRequest jobRequest, AuditEntityRequest auditEntityRequest);
}
