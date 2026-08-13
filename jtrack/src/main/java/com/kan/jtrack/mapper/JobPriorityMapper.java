package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.response.JobPriorityResponse;
import com.kan.jtrack.entity.JobPriority;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface JobPriorityMapper {
    JobPriorityResponse toJobPriorityResponse(JobPriority jobPriority);
}
