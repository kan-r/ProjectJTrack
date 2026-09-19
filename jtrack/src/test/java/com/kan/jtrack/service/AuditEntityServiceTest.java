package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditEntityServiceTest {

    private static final String USER_ID = "user-1";
    private static final String FIRST_NAME = "Kan";
    private static final String LAST_NAME = "Ranganathan";
    private static final String EMAIL = "kr@example.com";

    @Mock
    private UserService userService;

    @InjectMocks
    private AuditEntityService auditEntityService;

    @Test
    void generateAuditEntityRequest_shouldPopulateAuditEntityRequestFieldsFromCurrentUser() {
        UserResponse user = generateUserResponse();
        when(userService.getCurrentUser()).thenReturn(user);

        LocalDateTime now = LocalDateTime.of(2026, 9, 19, 10, 0, 0);

        try (MockedStatic<LocalDateTime> mockedNow = mockStatic(LocalDateTime.class)) {
            mockedNow.when(LocalDateTime::now).thenReturn(now);

            AuditEntityRequest result = auditEntityService.generateAuditEntityRequest();

            assertNotNull(result);
            assertEquals(USER_ID, result.getCreatedBy());
            assertEquals(USER_ID, result.getUpdatedBy());
            assertEquals(now, result.getCreatedAt());
            assertEquals(now, result.getUpdatedAt());
        }

        verify(userService, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userService);
    }

    private UserResponse generateUserResponse() {
        return UserResponse.builder()
                           .id(USER_ID)
                           .firstName(FIRST_NAME)
                           .lastName(LAST_NAME)
                           .email(EMAIL)
                           .build();
    }
}