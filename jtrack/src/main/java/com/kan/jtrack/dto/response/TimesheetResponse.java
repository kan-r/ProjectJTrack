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
@JsonPropertyOrder({
        "id",
        "userId",
        "userName",
        "jobId",
        "jobName",
        "workedDate",
        "workedHours"
})
public class TimesheetResponse extends AuditEntityResponse {
    private Integer id;
    private String userId;
    private String userName;
    private Integer jobId;
    private String jobName;
    private LocalDate workedDate;
    private Double workedHours;
}
