package com.kan.jtrack.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {
    private Integer sprintId;
    private String name;
    private String description;
    private String typeCode;
    private String priorityCode;
    private String statusCode;
    private String assignedTo;
    private Double estimatedHours;
    private Double actualHours;
    private Integer parentId;
}
