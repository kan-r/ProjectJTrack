package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.SprintRequest;
import com.kan.jtrack.dto.response.SprintResponse;
import com.kan.jtrack.entity.Sprint;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SprintMapper {

    SprintResponse toSprintResponse(Sprint sprint);

    @Mapping(target = "createdAt", source = "auditEntityRequest.createdAt")
    @Mapping(target = "createdBy", source = "auditEntityRequest.createdBy")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Sprint toSprint(SprintRequest sprintRequest, AuditEntityRequest auditEntityRequest);

    @Mapping(target = "updatedAt", source = "auditEntityRequest.updatedAt")
    @Mapping(target = "updatedBy", source = "auditEntityRequest.updatedBy")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToSprint(@MappingTarget Sprint sprint, SprintRequest sprintRequest, AuditEntityRequest auditEntityRequest);
}
