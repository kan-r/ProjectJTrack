package com.kan.jtrack.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRequest {
    private String userId;
    private Integer jobId;
    private LocalDate workedDate;
    private Double workedHours;
}
