package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USER_ID_1 = "user-1";
    private static final String FIRST_NAME_1 = "Kan";
    private static final String LAST_NAME_1 = "Ranganathan";
    private static final String EMAIL_1 = "kr@example.com";

    private static final String USER_ID_2 = "user-2";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Smith";
    private static final String EMAIL_2 = "js@example.com";

    private static final Instant TOKEN_ISSUED_TIME = Instant.now();
    private static final Instant TOKEN_EXPIRES_TIME = TOKEN_ISSUED_TIME.plusSeconds(300);

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void tearDown() {
        // Clear any SecurityContext modifications done by tests
        SecurityContextHolder.clearContext();
    }

    // ============ getAllUsers Tests ============

    @Test
    void getAllUsers_whenUsersExist_shouldReturnUserResponseList() {
        UserRepresentation userRepresentation1 = generateUserRepresentation1();
        UserRepresentation userRepresentation2 = generateUserRepresentation2();

        UserResponse userResponse1 = generateUserResponse1();
        UserResponse userResponse2 = generateUserResponse2();

        when(keycloakService.getAllUsers()).thenReturn(List.of(userRepresentation1, userRepresentation2));
        when(userMapper.toUserResponse(userRepresentation1)).thenReturn(userResponse1);
        when(userMapper.toUserResponse(userRepresentation2)).thenReturn(userResponse2);

        List<UserResponse> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(userResponse1));
        assertTrue(result.contains(userResponse2));
    }

    @Test
    void getAllUsers_whenNoUsersExist_shouldReturnEmptyList() {
        when(keycloakService.getAllUsers()).thenReturn(List.of());

        List<UserResponse> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getUserById Tests ============

    @ParameterizedTest
    @NullSource
    void getUserById_whenIdIsNull_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> userService.getUserById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void getUserById_whenIdIsBlank_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> userService.getUserById(id));
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUserResponse() {
        UserRepresentation userRepresentation = generateUserRepresentation1();
        UserResponse userResponse = generateUserResponse1();

        when(keycloakService.getUserById(USER_ID_1)).thenReturn(userRepresentation);
        when(userMapper.toUserResponse(userRepresentation)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(USER_ID_1);

        assertNotNull(result);
        assertEquals(userResponse, result);
    }

    // ============ getCurrentUser Tests ============

    @Test
    void getCurrentUser_whenPrincipalIsJwt_shouldReturnPopulatedUserResponse() {
        Jwt jwt = generateJwt();
        UserResponse userResponse = generateUserResponse1();
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);

        UserResponse result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(userResponse, result);
    }

    @Test
    void getCurrentUser_whenNoAuthenticationExists_shouldReturnEmptyUserResponse() {
        // No auth present in context
        SecurityContextHolder.clearContext();

        UserResponse result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(new UserResponse(), result);
    }

    @Test
    void getCurrentUser_whenPrincipalIsNotJwt_shouldReturnEmptyUserResponse() {
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(new Object());
        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserResponse result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(new UserResponse(), result);
    }

    // ============ Test data Creation Methods ============

    private UserRepresentation generateUserRepresentation1() {
        UserRepresentation user = new UserRepresentation();
        user.setId(USER_ID_1);
        user.setFirstName(FIRST_NAME_1);
        user.setLastName(LAST_NAME_1);
        user.setEmail(EMAIL_1);
        return user;
    }

    private UserRepresentation generateUserRepresentation2() {
        UserRepresentation user = new UserRepresentation();
        user.setId(USER_ID_2);
        user.setFirstName(FIRST_NAME_2);
        user.setLastName(LAST_NAME_2);
        user.setEmail(EMAIL_2);
        return user;
    }

    private UserResponse generateUserResponse1() {
        return UserResponse.builder()
                           .id(USER_ID_1)
                           .firstName(FIRST_NAME_1)
                           .lastName(LAST_NAME_1)
                           .email(EMAIL_1)
                           .build();
    }

    private UserResponse generateUserResponse2() {
        return UserResponse.builder()
                           .id(USER_ID_2)
                           .firstName(FIRST_NAME_2)
                           .lastName(LAST_NAME_2)
                           .email(EMAIL_2)
                           .build();
    }

    private Jwt generateJwt() {
        return Jwt.withTokenValue("token")
                  .header("alg", "RS256")
                  .issuedAt(TOKEN_ISSUED_TIME)
                  .expiresAt(TOKEN_EXPIRES_TIME)
                  .claim("sub", USER_ID_1)
                  .claim("given_name", FIRST_NAME_1)
                  .claim("family_name", LAST_NAME_1)
                  .claim("email", EMAIL_1)
                  .build();
    }
}