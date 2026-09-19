package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.response.UserResponse;
import com.kan.jtrack.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class,})
class UserControllerTest {

    private static final String ID_1 = "u1";
    private static final String USERNAME_1 = "Kan Ranganathan";
    private static final String EMAIL_1 = "kan@example.com";
    private static final String FIRST_NAME_1 = "Kan";
    private static final String LAST_NAME_1 = "Ranganathan";

    private static final String ID_2 = "u2";
    private static final String USERNAME_2 = "Jane Doe";
    private static final String EMAIL_2 = "jane@example.com";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Doe";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsers_returnsUsers() throws Exception {
        UserResponse u1 = generateUserResponse1();
        UserResponse u2 = generateUserResponse2();

        when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/users").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()", is(2)))
               .andExpect(jsonPath("$[0].id", is(ID_1)))
               .andExpect(jsonPath("$[0].username", is(USERNAME_1)))
               .andExpect(jsonPath("$[0].email", is(EMAIL_1)))
               .andExpect(jsonPath("$[1].id", is(ID_2)))
               .andExpect(jsonPath("$[1].username", is(USERNAME_2)))
               .andExpect(jsonPath("$[1].email", is(EMAIL_2)));
    }

    @Test
    void getUserById_returnsUser() throws Exception {
        UserResponse user = generateUserResponse1();

        when(userService.getUserById(ID_1)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", ID_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(ID_1)))
               .andExpect(jsonPath("$.username", is(USERNAME_1)))
               .andExpect(jsonPath("$.email", is(EMAIL_1)))
               .andExpect(jsonPath("$.firstName", is(FIRST_NAME_1)))
               .andExpect(jsonPath("$.lastName", is(LAST_NAME_1)));
    }

    @Test
    void getCurrentUser_returnsCurrentUser() throws Exception {
        UserResponse current = generateUserResponse1();

        when(userService.getCurrentUser()).thenReturn(current);

        mockMvc.perform(get("/users/current").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(ID_1)))
               .andExpect(jsonPath("$.username", is(USERNAME_1)))
               .andExpect(jsonPath("$.email", is(EMAIL_1)))
               .andExpect(jsonPath("$.firstName", is(FIRST_NAME_1)))
               .andExpect(jsonPath("$.lastName", is(LAST_NAME_1)));
    }

    // ============ Test data Generation Methods ============

    private UserResponse generateUserResponse1() {
        return UserResponse.builder()
                           .id(ID_1)
                           .username(USERNAME_1)
                           .email(EMAIL_1)
                           .firstName(FIRST_NAME_1)
                           .lastName(LAST_NAME_1)
                           .build();
    }

    private UserResponse generateUserResponse2() {
        return UserResponse.builder()
                           .id(ID_2)
                           .username(USERNAME_2)
                           .email(EMAIL_2)
                           .firstName(FIRST_NAME_2)
                           .lastName(LAST_NAME_2)
                           .build();
    }
}