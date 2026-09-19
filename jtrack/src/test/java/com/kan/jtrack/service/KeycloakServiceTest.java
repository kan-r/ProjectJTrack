package com.kan.jtrack.service;

import com.kan.jtrack.config.properties.KeycloakProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeycloakServiceTest {

    private static final String USER_ID_1 = "user-1";
    private static final String FIRST_NAME_1 = "Kan";
    private static final String LAST_NAME_1 = "Ranganathan";
    private static final String EMAIL_1 = "kr@example.com";

    private static final String USER_ID_2 = "user-2";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Smith";
    private static final String EMAIL_2 = "js@example.com";

    private static final String REALM = "jtrack";
    private static final String SERVER_URL = "http://localhost:8080";
    private static final String CLIENT_ID = "jtrack-client";
    private static final String CLIENT_SECRET = "secret";
    private static final String USERNAME = "kan";
    private static final String PASSWORD = "pass";

    @Mock
    private Keycloak keycloak;

    @Mock
    private KeycloakBuilder keycloakBuilder;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private KeycloakProperties properties;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @Mock
    private UserResource userResource;

    private KeycloakService keycloakService;

    @BeforeEach
    void setUp() {
        keycloakService = new KeycloakService(keycloak, properties);
        when(properties.getRealm()).thenReturn(REALM);
    }

    @Test
    void getAccessToken_shouldBuildClientAndReturnToken() {
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.setToken("test-token");

        when(properties.getServerUrl()).thenReturn(SERVER_URL);
        when(properties.getRealm()).thenReturn(REALM);
        when(properties.getClientId()).thenReturn(CLIENT_ID);
        when(properties.getClientSecret()).thenReturn(CLIENT_SECRET);

        when(keycloakBuilder.serverUrl(SERVER_URL)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.realm(REALM)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.grantType(anyString())).thenReturn(keycloakBuilder);
        when(keycloakBuilder.clientId(CLIENT_ID)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.clientSecret(CLIENT_SECRET)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.username(USERNAME)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.password(PASSWORD)).thenReturn(keycloakBuilder);
        when(keycloakBuilder.build()).thenReturn(keycloak);

        when(keycloak.tokenManager()).thenReturn(tokenManager);
        when(tokenManager.getAccessToken()).thenReturn(accessTokenResponse);

        try (MockedStatic<KeycloakBuilder> mockedBuilder = mockStatic(KeycloakBuilder.class)) {
            mockedBuilder.when(KeycloakBuilder::builder).thenReturn(keycloakBuilder);

            AccessTokenResponse actual = keycloakService.getAccessToken(USERNAME, PASSWORD);

            assertNotNull(actual);
            assertEquals("test-token", actual.getToken());
        }
    }

    @Test
    void getAllUsers_shouldReturnUsersFromRealm() {
        UserRepresentation user1 = generateUserRepresentation1();
        UserRepresentation user2 = generateUserRepresentation2();
        List<UserRepresentation> users = List.of(user1, user2);

        when(keycloak.realm(REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.list()).thenReturn(users);

        List<UserRepresentation> actual = keycloakService.getAllUsers();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(USER_ID_1, actual.get(0).getId());
        assertEquals(USER_ID_2, actual.get(1).getId());
    }

    @Test
    void getUserById_shouldReturnUserRepresentation() {
        UserRepresentation user = generateUserRepresentation1();

        when(keycloak.realm(REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get(USER_ID_1)).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(user);

        UserRepresentation actual = keycloakService.getUserById(USER_ID_1);

        assertNotNull(actual);
        assertEquals(user, actual);
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
}