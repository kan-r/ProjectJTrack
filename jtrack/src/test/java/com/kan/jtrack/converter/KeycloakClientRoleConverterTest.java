package com.kan.jtrack.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeycloakClientRoleConverterTest {

    private static final String CLIENT_ID = "jtrack-client";
    private static final String UNKNOWN_CLIENT_ID = "unknown-client";

    private static final String ADMIN = "admin";
    private static final String USER = "user";

    private static final String ROLE_ADMIN = "ROLE_admin";
    private static final String ROLE_USER = "ROLE_user";

    private KeycloakClientRoleConverter converter;

    @BeforeEach
    void setUp() {
        converter = new KeycloakClientRoleConverter(CLIENT_ID);
    }

    @Test
    void convert_shouldReturnAuthoritiesForClientRoles() {
        Jwt jwt = jwtWithResourceAccess(Map.of(
                CLIENT_ID, Map.of("roles", List.of(ADMIN, USER))
        ));

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toSet())
                                 .containsAll(Set.of(ROLE_ADMIN, ROLE_USER)));
    }

    @Test
    void convert_shouldReturnEmptyAuthoritiesWhenResourceAccessMissing() {
        Jwt jwt = jwtWithResourceAccess(null);

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities()
                                 .isEmpty());
    }

    @Test
    void convert_shouldReturnEmptyAuthoritiesWhenClientEntryMissing() {
        Jwt jwt = jwtWithResourceAccess(Map.of(
                UNKNOWN_CLIENT_ID, Map.of("roles", List.of(ADMIN))
        ));

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities()
                                 .isEmpty());
    }

    @Test
    void convert_shouldReturnEmptyAuthoritiesWhenRolesMissing() {
        Jwt jwt = jwtWithResourceAccess(Map.of(
                CLIENT_ID, Map.of()
        ));

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities()
                                 .isEmpty());
    }

    @Test
    void convert_shouldIgnoreNonStringRoles() {
        Jwt jwt = jwtWithResourceAccess(Map.of(
                CLIENT_ID, Map.of("roles", List.of(ADMIN, 123, true, USER))
        ));

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toSet())
                                 .containsAll(Set.of(ROLE_ADMIN, ROLE_USER)));
    }

    private Jwt jwtWithResourceAccess(Map<String, Object> resourceAccess) {
        Jwt.Builder builder = Jwt.withTokenValue("token")
                                 .header("alg", "none")
                                 .claim("sub", "user-1")
                                 .issuedAt(Instant.now())
                                 .expiresAt(Instant.now()
                                                   .plusSeconds(300));

        if (resourceAccess != null) {
            builder.claim("resource_access", resourceAccess);
        }

        return builder.build();
    }
}