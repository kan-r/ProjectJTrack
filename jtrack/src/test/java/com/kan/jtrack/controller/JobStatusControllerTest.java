package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.response.JobStatusResponse;
import com.kan.jtrack.service.JobStatusService;
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

@WebMvcTest(JobStatusController.class)
@Import({SecurityConfig.class,})
class JobStatusControllerTest {

    private static final String CODE_1 = "OPEN";
    private static final String CODE_2 = "IN_PROGRESS";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobStatusService jobStatusService;

    @Test
    void getAllJobStatuses_shouldReturnListOfStatuses_withStatusOk() throws Exception {
        JobStatusResponse response1 = generateJobStatusResponse(CODE_1);
        JobStatusResponse response2 = generateJobStatusResponse(CODE_2);

        when(jobStatusService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/jobStatuses").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getJobStatusById_shouldReturnStatus_withStatusOk() throws Exception {
        JobStatusResponse response = generateJobStatusResponse(CODE_1);

        when(jobStatusService.getById(CODE_1)).thenReturn(response);

        mockMvc.perform(get("/jobStatuses/{id}", CODE_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(CODE_1));
    }

    private JobStatusResponse generateJobStatusResponse(String code) {
        return JobStatusResponse.builder()
                                .code(code)
                                .build();
    }
}