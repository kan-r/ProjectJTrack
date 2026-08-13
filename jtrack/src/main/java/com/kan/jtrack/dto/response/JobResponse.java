package com.kan.jtrack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "sprintId", "name", "description", "typeCode", "priorityCode", "statusCode", "assignedTo", "estimatedHours", "actualHours", "parentId"})
public class JobResponse extends AuditEntityResponse {
    private Integer id;
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
