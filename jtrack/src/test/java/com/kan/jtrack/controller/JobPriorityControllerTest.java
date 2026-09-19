package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.response.JobPriorityResponse;
import com.kan.jtrack.service.JobPriorityService;
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

@WebMvcTest(JobPriorityController.class)
@Import({SecurityConfig.class,})
class JobPriorityControllerTest {

    private static final String CODE_1 = "LOW";
    private static final String CODE_2 = "HIGH";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobPriorityService jobPriorityService;

    @Test
    void getAll_shouldReturnListOfPriorities_withStatusOk() throws Exception {
        JobPriorityResponse response1 = generateJobPriorityResponse(CODE_1);
        JobPriorityResponse response2 = generateJobPriorityResponse(CODE_2);

        when(jobPriorityService.getAll()).thenReturn(List.of(response2, response1));

        mockMvc.perform(get("/jobPriorities").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnPriority_withStatusOk() throws Exception {
        JobPriorityResponse response = generateJobPriorityResponse(CODE_1);

        when(jobPriorityService.getById(CODE_1)).thenReturn(response);

        mockMvc.perform(get("/jobPriorities/{id}", CODE_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(CODE_1));
    }

    private JobPriorityResponse generateJobPriorityResponse(String code) {
        return JobPriorityResponse.builder()
                                  .code(code)
                                  .build();
    }
}