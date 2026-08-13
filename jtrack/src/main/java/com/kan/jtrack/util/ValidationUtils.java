package com.kan.jtrack.util;

import com.kan.jtrack.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;


public class ValidationUtils {

    public static void validateObjectNotNull(Object obj, String messagePrefix) {
        if (obj == null) {
            throw new ValidationException(messagePrefix + " must not be null");
        }
    }

    public static void validateStringNotBlank(String str, String messagePrefix) {
        if (str != null && StringUtils.isBlank(str)) {
            throw new ValidationException(messagePrefix + " must not be blank");
        }
    }

    public static void validateStringNotNullOrBlank(String str, String messagePrefix) {
        validateObjectNotNull(str, messagePrefix);
        validateStringNotBlank(str, messagePrefix);
    }
}
