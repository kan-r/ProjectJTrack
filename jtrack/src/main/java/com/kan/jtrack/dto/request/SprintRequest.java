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
public class SprintRequest {
    private String name;
    private String statusCode;
    private LocalDate startDate;
    private LocalDate endDate;
}
