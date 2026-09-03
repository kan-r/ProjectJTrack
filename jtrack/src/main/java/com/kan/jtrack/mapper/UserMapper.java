package com.kan.jtrack.mapper;

import com.kan.jtrack.dto.response.UserResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "fullName", ignore = true)
    UserResponse toUserResponse(UserRepresentation userRepresentation);

    @AfterMapping
    default void setFullName(UserRepresentation userRepresentation, @MappingTarget UserResponse userResponse) {
        String firstName = userRepresentation.getFirstName();
        String lastName = userRepresentation.getLastName();
        String fullName = (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
        userResponse.setFullName(fullName.trim());
    }
}
