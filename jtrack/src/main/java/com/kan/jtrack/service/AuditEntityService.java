package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuditEntityService {

    private final UserService userService;

    public AuditEntityService(UserService userService) {
        this.userService = userService;
    }

    public AuditEntityRequest generateAuditEntityRequest() {
        UserResponse user = userService.getCurrentUser();
        LocalDateTime now = LocalDateTime.now();

        return AuditEntityRequest.builder()
                                 .createdAt(now)
                                 .createdBy(user.getId())
                                 .updatedAt(now)
                                 .updatedBy(user.getId())
                                 .build();
    }
}
