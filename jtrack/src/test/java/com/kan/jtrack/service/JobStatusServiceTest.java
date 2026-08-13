package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobStatusResponse;
import com.kan.jtrack.entity.JobStatus;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.JobStatusMapper;
import com.kan.jtrack.repository.JobStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobStatusServiceTest {
    private static final String CODE_1 = "OPEN";
    private static final String DESCRIPTION_1 = "Open";
    private static final Integer DISPLAY_ORDER_1 = 1;

    private static final String CODE_2 = "CLOSED";
    private static final String DESCRIPTION_2 = "Closed";
    private static final Integer DISPLAY_ORDER_2 = 2;

    private static final String CODE_UNKNOWN = "UNKNOWN";

    @Mock
    private JobStatusRepository jobStatusRepository;

    @Mock
    private JobStatusMapper jobStatusMapper;

    @InjectMocks
    private JobStatusService jobStatusService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenJobStatusesExist_shouldReturnJobStatusResponseList() {
        JobStatus jobStatus1 = generateJobStatus1();
        JobStatus jobStatus2 = generateJobStatus2();

        JobStatusResponse jobStatusResponse1 = generateJobStatusResponse1();
        JobStatusResponse jobStatusResponse2 = generateJobStatusResponse2();

        when(jobStatusRepository.findAll()).thenReturn(List.of(jobStatus2, jobStatus1));
        when(jobStatusMapper.toJobStatusResponse(jobStatus1)).thenReturn(jobStatusResponse1);
        when(jobStatusMapper.toJobStatusResponse(jobStatus2)).thenReturn(jobStatusResponse2);

        List<JobStatusResponse> result = jobStatusService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(jobStatusResponse1, result.get(0));
        assertEquals(jobStatusResponse2, result.get(1));
    }

    @Test
    void getAll_whenNoJobStatusesExist_shouldReturnEmptyList() {
        when(jobStatusRepository.findAll()).thenReturn(List.of());

        List<JobStatusResponse> result = jobStatusService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobStatusService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void getById_whenIdIsBlank_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobStatusService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"OPEN", "open", "Open"})
    void getById_whenIdHasDifferentCase_shouldReturnJobStatusResponse(String id) {
        JobStatus jobStatus = generateJobStatus1();
        JobStatusResponse jobStatusResponse = generateJobStatusResponse1();

        when(jobStatusRepository.findById(id.toUpperCase())).thenReturn(of(jobStatus));
        when(jobStatusMapper.toJobStatusResponse(jobStatus)).thenReturn(jobStatusResponse);

        JobStatusResponse result = jobStatusService.getById(id);

        assertNotNull(result);
        assertEquals(jobStatusResponse, result);
    }

    @Test
    void getById_whenNoJobStatusExists_shouldThrowResourceNotFoundException() {
        when(jobStatusRepository.findById(CODE_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> jobStatusService.getById(CODE_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private JobStatus generateJobStatus1() {
        return JobStatus.builder()
                        .code(CODE_1)
                        .description(DESCRIPTION_1)
                        .displayOrder(DISPLAY_ORDER_1)
                        .build();
    }

    private JobStatus generateJobStatus2() {
        return JobStatus.builder()
                        .code(CODE_2)
                        .description(DESCRIPTION_2)
                        .displayOrder(DISPLAY_ORDER_2)
                        .build();
    }

    private JobStatusResponse generateJobStatusResponse1() {
        return JobStatusResponse.builder()
                                .code(CODE_1)
                                .description(DESCRIPTION_1)
                                .displayOrder(DISPLAY_ORDER_1)
                                .build();
    }

    private JobStatusResponse generateJobStatusResponse2() {
        return JobStatusResponse.builder()
                                .code(CODE_2)
                                .description(DESCRIPTION_2)
                                .displayOrder(DISPLAY_ORDER_2)
                                .build();
    }
}