package com.kan.jtrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.request.SprintRequest;
import com.kan.jtrack.dto.response.SprintResponse;
import com.kan.jtrack.service.SprintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SprintController.class)
@Import({SecurityConfig.class,})
class SprintControllerTest {

    private static final Integer ID_1 = 1;
    private static final String NAME_1 = "Sprint 1";

    private static final Integer ID_2 = 2;
    private static final String NAME_2 = "Sprint 2";

    private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_admin");
    private static final SimpleGrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority("ROLE_manager");
    private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_user");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SprintService sprintService;

    @Test
    void getAll_shouldReturnSprints_withStatusOk() throws Exception {
        SprintResponse response1 = generateSprintResponse(ID_1, NAME_1);
        SprintResponse response2 = generateSprintResponse(ID_2, NAME_2);

        when(sprintService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/sprints").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnSprint_withStatusOk() throws Exception {
        SprintResponse response = generateSprintResponse(ID_1, NAME_1);

        when(sprintService.getById(1)).thenReturn(response);

        mockMvc.perform(get("/sprints/{id}", ID_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_1));
    }

    @Test
    void create_asAdmin_shouldReturnCreated_withStatusCreated() throws Exception {
        SprintRequest request = generateSprintRequest(NAME_1);
        SprintResponse response = generateSprintResponse(ID_1, NAME_1);

        when(sprintService.create(request)).thenReturn(response);

        mockMvc.perform(post("/sprints")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_1));
    }

    @Test
    void create_withoutAdminOrManager_shouldBeForbidden() throws Exception {
        mockMvc.perform(post("/sprints")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void update_asManager_shouldReturnUpdated_withStatusOk() throws Exception {
        SprintRequest request = generateSprintRequest(NAME_2);
        SprintResponse response = generateSprintResponse(ID_1, NAME_2);

        when(sprintService.update(ID_1, request)).thenReturn(response);

        mockMvc.perform(put("/sprints/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_2));
    }

    @Test
    void update_withoutAdminOrManager_shouldBeForbidden() throws Exception {
        mockMvc.perform(put("/sprints/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void delete_asAdmin_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/sprints/{id}", ID_1)
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isNoContent());
    }

    @Test
    void delete_withoutAdminOrManager_shouldBeForbidden() throws Exception {
        mockMvc.perform(delete("/sprints/{id}", ID_1)
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isForbidden());
    }

    // ============ Test data Generation Methods ============

    private SprintRequest generateSprintRequest(String name) {
        return SprintRequest.builder()
                .name(name)
                .build();
    }

    private SprintResponse generateSprintResponse(Integer id, String name) {
        return SprintResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}