package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kan.jtrack.util.ValidationUtils.validateStringNotNullOrBlank;

@Slf4j
@Service
public class UserService {

    private final KeycloakService keycloakService;
    private final UserMapper userMapper;


    public UserService(KeycloakService keycloakService, UserMapper userMapper) {
        this.keycloakService = keycloakService;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAllUsers() {
        log.debug("getAllUsers()");

        return keycloakService.getAllUsers()
                       .stream()
                       .map(userMapper::toUserResponse)
                       .toList();
    }

    public UserResponse getUserById(String id) {
        log.debug("getUserById({})", id);

        validateStringNotNullOrBlank(id, "User id");
        UserRepresentation representation = keycloakService.getUserById(id);

        return userMapper.toUserResponse(representation);
    }

    public UserResponse getUserByIdIgnoreBlank(String id) {
        log.debug("getUserByIdIgnoreBlank({})", id);

        if(StringUtils.isBlank(id)) {
            return new UserResponse();
        }

        UserRepresentation representation = keycloakService.getUserById(id);
        return userMapper.toUserResponse(representation);
    }

    public UserResponse getCurrentUser() {
        log.debug("getCurrentUser()");

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return UserResponse.builder()
                    .id(jwt.getClaimAsString("sub"))
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .fullName(jwt.getClaimAsString("name"))
                    .email(jwt.getClaimAsString("email"))
                    .build();
        }

        return new UserResponse();
    }
}
