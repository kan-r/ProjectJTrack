package com.kan.jtrack.config;

import com.kan.jtrack.converter.KeycloakClientRoleConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SecurityConfigTest.TestController.class)
@Import({
        SecurityConfig.class,
        SecurityConfigTest.TestBeans.class,
        SecurityConfigTest.TestController.class
})
class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void tokenEndpoint_shouldPermitAll() throws Exception {
        mockMvc.perform(get("/token"))
               .andExpect(status().isOk());
    }

    @Test
    void nonTokenEndpoint_shouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/secure"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void nonTokenEndpoint_withJwt_shouldBeAllowed() throws Exception {
        mockMvc.perform(get("/secure").with(jwt()))
               .andExpect(status().isOk());
    }

    @Test
    void corsPreflight_shouldAllowConfiguredOriginMethodAndHeaders() throws Exception {
        mockMvc.perform(options("/secure").header(ORIGIN, "http://localhost:4200")
                                          .header(ACCESS_CONTROL_REQUEST_METHOD, "GET")
                                          .header(ACCESS_CONTROL_REQUEST_HEADERS, "Authorization,Content-Type"))
               .andExpect(status().isOk())
               .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }

    @TestConfiguration
    static class TestBeans {

        @Bean
        KeycloakClientRoleConverter keycloakClientRoleConverter() {
            KeycloakClientRoleConverter converter = mock(KeycloakClientRoleConverter.class);
            when(converter.convert(any(Jwt.class))).thenAnswer(invocation -> {
                Jwt jwt = invocation.getArgument(0);
                return new UsernamePasswordAuthenticationToken(jwt.getSubject(), "n/a", List.of());
            });
            return converter;
        }

        @Bean
        JwtDecoder jwtDecoder() {
            return token -> Jwt.withTokenValue(token)
                               .header("alg", "none")
                               .claim("sub", "test-user")
                               .build();
        }
    }

    @RestController
    static class TestController {

        @GetMapping("/token")
        String token() {
            return "ok";
        }

        @GetMapping("/secure")
        String secure() {
            return "secure";
        }
    }
}