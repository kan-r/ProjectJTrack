package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.response.SprintStatusResponse;
import com.kan.jtrack.entity.SprintStatus;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface SprintStatusMapper {
    SprintStatusResponse toSprintStatusResponse(SprintStatus sprintStatus);
}
