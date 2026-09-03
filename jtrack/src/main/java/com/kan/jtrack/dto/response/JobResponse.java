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
@JsonPropertyOrder({
        "id",
        "sprintId",
        "sprintName",
        "name",
        "description",
        "typeCode",
        "typeDescription",
        "priorityCode",
        "priorityDescription",
        "statusCode",
        "statusDescription",
        "assignedTo",
        "assignedToName",
        "estimatedHours",
        "actualHours",
        "parentId",
        "parentName"
})
public class JobResponse extends AuditEntityResponse {
    private Integer id;
    private Integer sprintId;
    private String sprintName;
    private String name;
    private String description;
    private String typeCode;
    private String typeDescription;
    private String priorityCode;
    private String priorityDescription;
    private String statusCode;
    private String statusDescription;
    private String assignedTo;
    private String assignedToName;
    private Double estimatedHours;
    private Double actualHours;
    private Integer parentId;
    private String parentName;
}
