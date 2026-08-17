package com.kan.jtrack.service;

import com.kan.jtrack.config.properties.KeycloakProperties;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakProperties properties;


    public KeycloakService(Keycloak keycloak, KeycloakProperties properties) {
        this.keycloak = keycloak;
        this.properties = properties;
    }

    public AccessTokenResponse getAccessToken(String username, String password) {
        log.debug("getAccessToken({}, ****)", username);

        try (Keycloak keycloak = KeycloakBuilder.builder()
                                                .serverUrl(properties.getServerUrl())
                                                .realm(properties.getRealm())
                                                .grantType(OAuth2Constants.PASSWORD)
                                                .clientId(properties.getClientId())
                                                .clientSecret(properties.getClientSecret())
                                                .username(username)
                                                .password(password)
                                                .build()) {
            return keycloak.tokenManager()
                           .getAccessToken();
        }
    }

    public List<UserRepresentation> getAllUsers() {
        log.debug("getAllUsers()");

        return keycloak.realm(properties.getRealm())
                       .users()
                       .list();
    }

    public  UserRepresentation getUserById(String id) {
        log.debug("getUserById({})", id);

        return keycloak.realm(properties.getRealm())
                       .users()
                       .get(id)
                       .toRepresentation();
    }
}
