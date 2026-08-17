package com.kan.jtrack.controller;

import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public List<UserResponse> getAllUsers() {
        log.info("getAllUsersFromKeycloak()");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('manager')")
    public UserResponse getUserById(@PathVariable String id) {
        log.debug("getById({})", id);
        return userService.getUserById(id);
    }

    @GetMapping("/current")
    public UserResponse getCurrentUser(){
        log.info("getCurrentUser()");
        return userService.getCurrentUser();
    }
}
