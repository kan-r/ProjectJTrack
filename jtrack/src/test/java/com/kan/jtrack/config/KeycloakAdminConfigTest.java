package com.kan.jtrack.config;

import com.kan.jtrack.config.properties.KeycloakProperties;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = {KeycloakAdminConfig.class, KeycloakAdminConfigTest.TestPropsConfig.class})
class KeycloakAdminConfigTest {

    private static final String SERVER_URL = "http://localhost:8080/auth/realms/jtrack";
    private static final String REALM = "jtrack";
    private static final String CLIENT_ID = "jtrack-client";
    private static final String CLIENT_SECRET = "jtrack-secret";

    @Autowired
    private Keycloak keycloak;

    @Test
    void shouldCreateKeycloakBean() {
        assertNotNull(keycloak);
    }

    @TestConfiguration
    static class TestPropsConfig {

        @Bean
        KeycloakProperties keycloakProperties() {
            KeycloakProperties properties = new KeycloakProperties();
            properties.setServerUrl(SERVER_URL);
            properties.setRealm(REALM);
            properties.setClientId(CLIENT_ID);
            properties.setClientSecret(CLIENT_SECRET);
            return properties;
        }
    }
}