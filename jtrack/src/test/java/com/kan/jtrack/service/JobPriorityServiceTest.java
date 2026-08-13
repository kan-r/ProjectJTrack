package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobPriorityResponse;
import com.kan.jtrack.entity.JobPriority;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.JobPriorityMapper;
import com.kan.jtrack.repository.JobPriorityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobPriorityServiceTest {

    private static final String CODE_1 = "LOW";
    private static final String DESCRIPTION_1 = "Low Priority";
    private static final Integer DISPLAY_ORDER_1 = 1;

    private static final String CODE_2 = "HIGH";
    private static final String DESCRIPTION_2 = "High Priority";
    private static final Integer DISPLAY_ORDER_2 = 2;

    private static final String CODE_UNKNOWN = "UNKNOWN";

    @Mock
    private JobPriorityRepository jobPriorityRepository;

    @Mock
    private JobPriorityMapper jobPriorityMapper;

    @InjectMocks
    private JobPriorityService jobPriorityService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenJobPrioritiesExist_shouldReturnJobPriorityResponseList() {
        JobPriority jobPriority1 = generateJobPriority1();
        JobPriority jobPriority2 = generateJobPriority2();

        JobPriorityResponse jobPriorityResponse1 = generateJobPriorityResponse1();
        JobPriorityResponse jobPriorityResponse2 = generateJobPriorityResponse2();

        when(jobPriorityRepository.findAll()).thenReturn(List.of(jobPriority2, jobPriority1));
        when(jobPriorityMapper.toJobPriorityResponse(jobPriority1)).thenReturn(jobPriorityResponse1);
        when(jobPriorityMapper.toJobPriorityResponse(jobPriority2)).thenReturn(jobPriorityResponse2);

        List<JobPriorityResponse> result = jobPriorityService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(jobPriorityResponse1, result.get(0));
        assertEquals(jobPriorityResponse2, result.get(1));
    }

    @Test
    void getAll_whenNoJobPrioritiesExist_shouldReturnEmptyList() {
        when(jobPriorityRepository.findAll()).thenReturn(List.of());

        List<JobPriorityResponse> result = jobPriorityService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobPriorityService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void getById_whenIdIsBlank_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobPriorityService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"LOW", "low", "Low"})
    void getById_whenIdHasDifferentCase_shouldReturnJobPriorityResponse(String id) {
        JobPriority jobPriority = generateJobPriority1();
        JobPriorityResponse jobPriorityResponse = generateJobPriorityResponse1();

        when(jobPriorityRepository.findById("LOW")).thenReturn(Optional.of(jobPriority));
        when(jobPriorityMapper.toJobPriorityResponse(jobPriority)).thenReturn(jobPriorityResponse);

        JobPriorityResponse result = jobPriorityService.getById(id);

        assertNotNull(result);
        assertEquals(jobPriorityResponse, result);
    }

    @Test
    void getById_whenNoJobPriorityExists_shouldThrowResourceNotFoundException() {
        when(jobPriorityRepository.findById(CODE_UNKNOWN)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> jobPriorityService.getById(CODE_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private JobPriority generateJobPriority1() {
        return JobPriority.builder()
                          .code(CODE_1)
                          .description(DESCRIPTION_1)
                          .displayOrder(DISPLAY_ORDER_1)
                          .build();
    }

    private JobPriority generateJobPriority2() {
        return JobPriority.builder()
                          .code(CODE_2)
                          .description(DESCRIPTION_2)
                          .displayOrder(DISPLAY_ORDER_2)
                          .build();
    }

    private JobPriorityResponse generateJobPriorityResponse1() {
        return JobPriorityResponse.builder()
                                  .code(CODE_1)
                                  .description(DESCRIPTION_1)
                                  .displayOrder(DISPLAY_ORDER_1)
                                  .build();
    }

    private JobPriorityResponse generateJobPriorityResponse2() {
        return JobPriorityResponse.builder()
                                  .code(CODE_2)
                                  .description(DESCRIPTION_2)
                                  .displayOrder(DISPLAY_ORDER_2)
                                  .build();
    }
}