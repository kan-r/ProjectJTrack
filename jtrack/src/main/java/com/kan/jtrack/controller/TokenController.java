package com.kan.jtrack.controller;

import com.kan.jtrack.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TokenController {

    private final KeycloakService keycloakService;

    public TokenController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AccessTokenResponse getToken(@RequestParam("username") String username, @RequestParam("password") String password) {
        log.debug("getToken({}, ****)", username);
        return keycloakService.getAccessToken(username, password);
    }
}
