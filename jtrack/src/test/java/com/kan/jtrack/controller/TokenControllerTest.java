package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.service.KeycloakService;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TokenController.class)
@Import({SecurityConfig.class,})
class TokenControllerTest {

    public static final String USERNAME = "john";
    public static final String PASSWORD = "secret";
    public static final String ACCESS_TOKEN = "access-token-123";
    public static final String REFRESH_TOKEN = "refresh-token-456";
    public static final String TOKEN_TYPE = "Bearer";
    public static final int EXPIRES_IN = 300;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KeycloakService keycloakService;

    @Test
    void getToken_success() throws Exception {
        AccessTokenResponse tokenResponse = generateTokenResponse();

        when(keycloakService.getAccessToken(USERNAME, PASSWORD)).thenReturn(tokenResponse);

        mockMvc.perform(post("/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", USERNAME)
                                .param("password", PASSWORD))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.access_token", is(ACCESS_TOKEN)))
               .andExpect(jsonPath("$.refresh_token", is(REFRESH_TOKEN)))
               .andExpect(jsonPath("$.token_type", is(TOKEN_TYPE)))
               .andExpect(jsonPath("$.expires_in", is(EXPIRES_IN)));
    }

    @Test
    void getToken_missingUsername_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("password", PASSWORD))
               .andExpect(status().isBadRequest());

        verify(keycloakService, never()).getAccessToken(anyString(), anyString());
    }

    @Test
    void getToken_missingPassword_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", "john"))
               .andExpect(status().isBadRequest());

        verify(keycloakService, never()).getAccessToken(anyString(), anyString());
    }

    private AccessTokenResponse generateTokenResponse() {
        AccessTokenResponse tokenResponse = new AccessTokenResponse();
        tokenResponse.setToken(ACCESS_TOKEN);
        tokenResponse.setRefreshToken(REFRESH_TOKEN);
        tokenResponse.setTokenType(TOKEN_TYPE);
        tokenResponse.setExpiresIn(EXPIRES_IN);
        return tokenResponse;
    }
}