package com.kan.jtrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.request.JobRequest;
import com.kan.jtrack.dto.request.JobStatusUpdateRequest;
import com.kan.jtrack.dto.response.JobResponse;
import com.kan.jtrack.service.JobService;
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

@WebMvcTest(JobController.class)
@Import({SecurityConfig.class,})
class JobControllerTest {

    private static final Integer ID_1 = 1;
    private static final String NAME_1 = "Main Job";
    private static final String STATUS_CODE_1 = "BACKLOG";

    private static final Integer ID_2 = 2;
    private static final String NAME_2 = "Sub Job";

    private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_admin");
    private static final SimpleGrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority("ROLE_manager");
    private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_user");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Test
    void getAll_shouldReturnJobs_withStatusOk() throws Exception {
        JobResponse response1 = generateJobResponse(ID_1, NAME_1);
        JobResponse response2 = generateJobResponse(ID_2, NAME_2);

        when(jobService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/jobs").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnJob_withStatusOk() throws Exception {
        JobResponse response = generateJobResponse(ID_1, NAME_1);

        when(jobService.getById(1)).thenReturn(response);

        mockMvc.perform(get("/jobs/{id}", ID_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_1));
    }

    @Test
    void create_asAdmin_shouldReturnCreated_withStatusCreated() throws Exception {
        JobRequest request = generateJobRequest(NAME_1);
        JobResponse response = generateJobResponse(ID_1, NAME_1);

        when(jobService.create(request)).thenReturn(response);

        mockMvc.perform(post("/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_1));
    }

    @Test
    void create_withoutAdminOrManager_shouldBeForbidden() throws Exception {
        mockMvc.perform(post("/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void update_asManager_shouldReturnUpdated_withStatusOk() throws Exception {
        JobRequest request = generateJobRequest(NAME_2);
        JobResponse response = generateJobResponse(ID_1, NAME_2);

        when(jobService.update(ID_1, request)).thenReturn(response);

        mockMvc.perform(put("/jobs/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.name").value(NAME_2));
    }

    @Test
    void update_withoutAdminOrManager_shouldBeForbidden() throws Exception {
        mockMvc.perform(put("/jobs/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void updateStatus_shouldReturnUpdated_withStatusOk() throws Exception {
        JobStatusUpdateRequest request = generateJobStatusUpdateRequest();
        JobResponse response = generateJobResponse(ID_1, NAME_1);
        response.setStatusCode(STATUS_CODE_1);

        when(jobService.updateStatus(ID_1, request)).thenReturn(response);

        mockMvc.perform(patch("/jobs/{id}/status", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.statusCode").value(STATUS_CODE_1));
    }

    @Test
    void delete_asAdmin_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/jobs/{id}", ID_1)
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isNoContent());
    }

    @Test
    void delete_withoutAdmin_shouldBeForbidden() throws Exception {
        mockMvc.perform(delete("/jobs/{id}", ID_1)
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isForbidden());
    }

    // ============ Test data Generation Methods ============

    private JobRequest generateJobRequest(String name) {
        return JobRequest.builder()
                         .name(name)
                         .build();
    }

    private JobStatusUpdateRequest generateJobStatusUpdateRequest() {
        JobStatusUpdateRequest request = new JobStatusUpdateRequest();
        request.setStatusCode(STATUS_CODE_1);
        return request;
    }

    private JobResponse generateJobResponse(Integer id, String name) {
        return JobResponse.builder()
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