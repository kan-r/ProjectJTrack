package com.kan.jtrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kan.jtrack.config.SecurityConfig;
import com.kan.jtrack.dto.request.TimesheetRequest;
import com.kan.jtrack.dto.response.TimesheetResponse;
import com.kan.jtrack.service.TimesheetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimesheetController.class)
@Import({SecurityConfig.class,})
class TimesheetControllerTest {

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;

    private static final String USER_ID = "user1";
    private static final Integer JOB_ID = 101;

    private static final LocalDate WORKED_DATE_1 = LocalDate.of(2026, 9, 15);
    private static final Double WORKED_HOURS_1 = 8.0;
    private static final LocalDate WORKED_DATE_2 = LocalDate.of(2026, 9, 16);
    private static final Double WORKED_HOURS_2 = 7.5;

    private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_admin");
    private static final SimpleGrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority("ROLE_manager");
    private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_user");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TimesheetService timesheetService;

    @Test
    void getAll_shouldReturnTimesheets_withStatusOk() throws Exception {
        TimesheetResponse response1 = generateTimesheetResponse(ID_1, WORKED_DATE_1, WORKED_HOURS_1);
        TimesheetResponse response2 = generateTimesheetResponse(ID_2, WORKED_DATE_2, WORKED_HOURS_2);

        when(timesheetService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/timesheets").with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnTimesheet_withStatusOk() throws Exception {
        TimesheetResponse response = generateTimesheetResponse(ID_1, WORKED_DATE_1, WORKED_HOURS_1);

        when(timesheetService.getById(1)).thenReturn(response);

        mockMvc.perform(get("/timesheets/{id}", ID_1).with(jwt()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.workedDate").value(WORKED_DATE_1.toString()))
               .andExpect(jsonPath("$.workedHours").value(WORKED_HOURS_1));
    }

    @Test
    void create_asAdmin_shouldReturnCreated_withStatusCreated() throws Exception {
        TimesheetRequest request = generateTimesheetRequest(WORKED_DATE_1, WORKED_HOURS_1);
        TimesheetResponse response = generateTimesheetResponse(ID_1, WORKED_DATE_1, WORKED_HOURS_1);

        when(timesheetService.create(request)).thenReturn(response);

        mockMvc.perform(post("/timesheets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.workedDate").value(WORKED_DATE_1.toString()))
               .andExpect(jsonPath("$.workedHours").value(WORKED_HOURS_1));
    }

    @Test
    void create_withoutAdminOrUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(post("/timesheets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void update_asUser_shouldReturnUpdated_withStatusOk() throws Exception {
        TimesheetRequest request = generateTimesheetRequest(WORKED_DATE_2, WORKED_HOURS_2);
        TimesheetResponse response = generateTimesheetResponse(ID_1, WORKED_DATE_2, WORKED_HOURS_2);

        when(timesheetService.update(ID_1, request)).thenReturn(response);

        mockMvc.perform(put("/timesheets/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(request))
                                .with(jwt().authorities(ROLE_USER)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ID_1))
               .andExpect(jsonPath("$.workedDate").value(WORKED_DATE_2.toString()))
               .andExpect(jsonPath("$.workedHours").value(WORKED_HOURS_2));
    }

    @Test
    void update_withoutAdminOrUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(put("/timesheets/{id}", ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isForbidden());
    }

    @Test
    void delete_asAdmin_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/timesheets/{id}", ID_1)
                                .with(jwt().authorities(ROLE_ADMIN)))
               .andExpect(status().isNoContent());
    }

    @Test
    void delete_withoutAdminOrUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(delete("/timesheets/{id}", ID_1)
                                .with(jwt().authorities(ROLE_MANAGER)))
               .andExpect(status().isForbidden());
    }

    // ============ Test data Generation Methods ============

    private TimesheetRequest generateTimesheetRequest(LocalDate workedDate, Double workedHours) {
        return TimesheetRequest.builder()
                               .userId(USER_ID)
                               .jobId(JOB_ID)
                               .workedDate(workedDate)
                               .workedHours(workedHours)
                               .build();
    }

    private TimesheetResponse generateTimesheetResponse(Integer id, LocalDate workedDate, Double workedHours) {
        return TimesheetResponse.builder()
                                .id(id)
                                .userId(USER_ID)
                                .jobId(JOB_ID)
                                .workedDate(workedDate)
                                .workedHours(workedHours)
                                .build();
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper.writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}