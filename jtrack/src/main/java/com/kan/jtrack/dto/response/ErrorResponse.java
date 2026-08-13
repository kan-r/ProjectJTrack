package com.kan.jtrack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"error", "message"})
public class ErrorResponse {
    private String error;
    private String message;
}
