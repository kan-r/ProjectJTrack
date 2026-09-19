package com.kan.jtrack.exception;

import com.kan.jtrack.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException_shouldReturn404AndErrorResponse() {
        String jobNotFound = "Job not found";

        ResourceNotFoundException ex = new ResourceNotFoundException(jobNotFound);
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ResourceNotFoundException", response.getBody().getError());
        assertEquals(jobNotFound, response.getBody().getMessage());
    }

    @Test
    void handleValidationException_shouldReturn400AndErrorResponse() {
        String invalidSprintDate = "Invalid sprint date";

        ValidationException ex = new ValidationException(invalidSprintDate);
        ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ValidationException", response.getBody().getError());
        assertEquals(invalidSprintDate, response.getBody().getMessage());
    }

    @Test
    void handleForbiddenException_shouldReturn403AndErrorResponse() {
        String accessDenied = "Access denied";

        AuthorizationDeniedException ex = new AuthorizationDeniedException(accessDenied);
        ResponseEntity<ErrorResponse> response = handler.handleForbiddenException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ForbiddenException", response.getBody().getError());
        assertEquals(accessDenied, response.getBody().getMessage());
    }

    @Test
    void handleRuntimeException_shouldReturn400AndUseRootCauseMessage() {
        String rootCauseMessage = "Root cause message";

        RuntimeException ex = new RuntimeException(
                "Wrapper",
                new IllegalStateException(rootCauseMessage)
        );

        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("RuntimeException", response.getBody().getError());
        assertEquals(rootCauseMessage, response.getBody().getMessage());
    }

    @Test
    void handleRuntimeException_shouldReturn400AndUseOwnMessageWhenNoCause() {
        String runtimeMessage = "Direct runtime message";

        RuntimeException ex = new RuntimeException(runtimeMessage);
        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("RuntimeException", response.getBody().getError());
        assertEquals(runtimeMessage, response.getBody().getMessage());
    }
}