package com.kan.jtrack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "statusCode", "statusDescription", "startDate", "endDate"})
public class SprintResponse extends AuditEntityResponse {
    private Integer id;
    private String name;
    private String statusCode;
    private String statusDescription;
    private LocalDate startDate;
    private LocalDate endDate;
}
