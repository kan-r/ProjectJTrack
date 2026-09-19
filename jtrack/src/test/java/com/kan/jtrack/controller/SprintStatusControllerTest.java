package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.response.SprintStatusResponse;
import com.kan.jtrack.service.SprintStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SprintStatusController.class)
@Import({SecurityConfig.class,})
class SprintStatusControllerTest {

    private static final String CODE_1 = "PLANNING";
    private static final String CODE_2 = "ACTIVE";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SprintStatusService sprintStatusService;

    @Test
    void getAllSprintStatuses_shouldReturnListOfStatuses_withStatusOk() throws Exception {
        SprintStatusResponse response1 = generateSprintStatusResponse(CODE_1);
        SprintStatusResponse response2 = generateSprintStatusResponse(CODE_2);

        when(sprintStatusService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/sprintStatuses").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getSprintStatusById_shouldReturnStatus_withStatusOk() throws Exception {
        SprintStatusResponse response = generateSprintStatusResponse(CODE_1);

        when(sprintStatusService.getById(CODE_1)).thenReturn(response);

        mockMvc.perform(get("/sprintStatuses/{id}", CODE_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(CODE_1));
    }

    private SprintStatusResponse generateSprintStatusResponse(String code) {
        return SprintStatusResponse.builder()
                                   .code(code)
                                   .build();
    }
}