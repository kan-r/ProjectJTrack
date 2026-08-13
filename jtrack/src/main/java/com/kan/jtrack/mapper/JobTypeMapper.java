package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.response.JobTypeResponse;
import com.kan.jtrack.entity.JobType;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface JobTypeMapper {
    JobTypeResponse toJobTypeResponse(JobType jobType);
}
