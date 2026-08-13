package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.response.JobStatusResponse;
import com.kan.jtrack.entity.JobStatus;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface JobStatusMapper {
    JobStatusResponse toJobStatusResponse(JobStatus jobStatus);
}
