package com.kan.jtrack.util;

import com.kan.jtrack.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.kan.jtrack.util.ValidationUtils.validateObjectNotNull;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    private static final String MESSAGE_PREFIX = "Field";

    // ============ validateObjectNotNull() Tests ============

    @Test
    void validateObjectNotNull_whenObjectIsNotNull_shouldNotThrowException() {
        assertDoesNotThrow(() -> validateObjectNotNull(new Object(), MESSAGE_PREFIX));
    }

    @ParameterizedTest
    @NullSource
    void validateObjectNotNull_whenObjectIsNull_shouldThrowValidationException(Object obj) {
        ValidationException exception
                = assertThrows(ValidationException.class, () -> validateObjectNotNull(obj, MESSAGE_PREFIX));

        assertEquals(MESSAGE_PREFIX + " must not be null", exception.getMessage());
    }

    // ============ validateStringNotBlank() Tests ============

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"abc", " abc ", "1", "valid"})
    void validateStringNotBlank_whenStringIsNullOrNotBlank_shouldNotThrowException(String value) {
        assertDoesNotThrow(() -> ValidationUtils.validateStringNotBlank(value, MESSAGE_PREFIX));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void validateStringNotBlank_whenStringIsBlank_shouldThrowValidationException(String value) {
        ValidationException exception
                = assertThrows(ValidationException.class, () -> ValidationUtils.validateStringNotBlank(value, MESSAGE_PREFIX));

        assertEquals(MESSAGE_PREFIX + " must not be blank", exception.getMessage());
    }

    // ============ validateStringNotNullOrBlank() Tests ============

    @ParameterizedTest
    @ValueSource(strings = {"abc", " abc ", "1", "valid"})
    void validateStringNotNullOrBlank_whenStringIsNotBlank_shouldNotThrowException(String value) {
        assertDoesNotThrow(() -> ValidationUtils.validateStringNotNullOrBlank(value, MESSAGE_PREFIX));
    }

    @ParameterizedTest
    @NullSource
    void validateStringNotNullOrBlank_whenStringIsNull_shouldThrowValidationException(String value) {
        ValidationException exception
                = assertThrows(ValidationException.class, () -> ValidationUtils.validateStringNotNullOrBlank(value, MESSAGE_PREFIX));

        assertEquals(MESSAGE_PREFIX + " must not be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void validateStringNotNullOrBlank_whenStringIsBlank_shouldThrowValidationException(String value) {
        ValidationException exception
                = assertThrows(ValidationException.class, () -> ValidationUtils.validateStringNotNullOrBlank(value, MESSAGE_PREFIX));

        assertEquals(MESSAGE_PREFIX + " must not be blank", exception.getMessage());
    }
}