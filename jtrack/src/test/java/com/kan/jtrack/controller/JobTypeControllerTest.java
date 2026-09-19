package com.kan.jtrack.controller;

import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.response.JobTypeResponse;
import com.kan.jtrack.service.JobTypeService;
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

@WebMvcTest(JobTypeController.class)
@Import({SecurityConfig.class,})
class JobTypeControllerTest {

    private static final String CODE_1 = "TASK";
    private static final String CODE_2 = "SUB_TASK";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobTypeService jobTypeService;

    @Test
    void getAllJobTypes_shouldReturnListOfTypes_withStatusOk() throws Exception {
        JobTypeResponse response1 = generateJobTypeResponse(CODE_1);
        JobTypeResponse response2 = generateJobTypeResponse(CODE_2);

        when(jobTypeService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/jobTypes").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnType_withStatusOk() throws Exception {
        JobTypeResponse response = generateJobTypeResponse(CODE_1);

        when(jobTypeService.getById(CODE_1)).thenReturn(response);

        mockMvc.perform(get("/jobTypes/{id}", CODE_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(CODE_1));
    }

    private JobTypeResponse generateJobTypeResponse(String code) {
        return JobTypeResponse.builder()
                              .code(code)
                              .build();
    }
}