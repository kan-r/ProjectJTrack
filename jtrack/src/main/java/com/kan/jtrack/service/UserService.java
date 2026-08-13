package com.kan.jtrack.service;

import com.kan.jtrack.config.properties.KeycloakProperties;
import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateObjectNotNull;
import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class UserService {

    private final Keycloak keycloak;
    private final KeycloakProperties properties;
    private final UserMapper userMapper;


    public UserService(Keycloak keycloak, KeycloakProperties properties, UserMapper userMapper) {
        this.keycloak = keycloak;
        this.properties = properties;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAllUsersFromKeycloak() {
        log.debug("getAllUsersFromKeycloak()");

        return keycloak.realm(properties.getRealm())
                       .users()
                       .list()
                       .stream()
                       .map(userMapper::toUserResponse)
                       .toList();
    }

    public UserResponse getUserByIdFromKeycloak(String id) {
        log.debug("getUserByIdFromKeycloak({})", id);

        validateStringNotNullOrBlank(id, "User id");
        UserRepresentation representation = keycloak.realm(properties.getRealm())
                                                    .users()
                                                    .get(id)
                                                    .toRepresentation();

        validateObjectNotNull(representation, "User");
        return userMapper.toUserResponse(representation);
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return UserResponse.builder()
                    .id(jwt.getClaimAsString("sub"))
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .email(jwt.getClaimAsString("email"))
                    .build();
        }

        return new UserResponse();
    }
}
