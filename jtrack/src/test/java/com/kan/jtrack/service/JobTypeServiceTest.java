package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.JobTypeResponse;
import com.kan.jtrack.entity.JobType;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.JobTypeMapper;
import com.kan.jtrack.repository.JobTypeRepository;
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
class JobTypeServiceTest {

    private static final String CODE_1 = "TASK";
    private static final String DESCRIPTION_1 = "Task";

    private static final String CODE_2 = "BUG";
    private static final String DESCRIPTION_2 = "Bug";

    private static final String CODE_UNKNOWN = "UNKNOWN";

    @Mock
    private JobTypeRepository jobTypeRepository;

    @Mock
    private JobTypeMapper jobTypeMapper;

    @InjectMocks
    private JobTypeService jobTypeService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenJobTypesExist_shouldReturnJobTypeResponseList() {
        JobType jobType1 = generateJobType1();
        JobType jobType2 = generateJobType2();

        JobTypeResponse jobTypeResponse1 = generateJobTypeResponse1();
        JobTypeResponse jobTypeResponse2 = generateJobTypeResponse2();

        when(jobTypeRepository.findAll()).thenReturn(List.of(jobType1, jobType2));
        when(jobTypeMapper.toJobTypeResponse(jobType1)).thenReturn(jobTypeResponse1);
        when(jobTypeMapper.toJobTypeResponse(jobType2)).thenReturn(jobTypeResponse2);

        List<JobTypeResponse> result = jobTypeService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(jobTypeResponse1));
        assertTrue(result.contains(jobTypeResponse2));
    }

    @Test
    void getAll_whenNoJobTypesExist_shouldReturnEmptyList() {
        when(jobTypeRepository.findAll()).thenReturn(List.of());

        List<JobTypeResponse> result = jobTypeService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobTypeService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void getById_whenIdIsBlank_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> jobTypeService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"OPEN", "open", "Open"})
    void getById_whenIdHasDifferentCase_shouldReturnJobTypeResponse(String id) {
        JobType jobType = generateJobType1();
        JobTypeResponse jobTypeResponse = generateJobTypeResponse1();

        when(jobTypeRepository.findById(id.toUpperCase())).thenReturn(of(jobType));
        when(jobTypeMapper.toJobTypeResponse(jobType)).thenReturn(jobTypeResponse);

        JobTypeResponse result = jobTypeService.getById(id);

        assertNotNull(result);
        assertEquals(jobTypeResponse, result);
    }

    @Test
    void getById_whenNoJobTypeExists_shouldThrowResourceNotFoundException() {
        when(jobTypeRepository.findById(CODE_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> jobTypeService.getById(CODE_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private JobType generateJobType1() {
        return JobType.builder()
                      .code(CODE_1)
                      .description(DESCRIPTION_1)
                      .build();
    }

    private JobType generateJobType2() {
        return JobType.builder()
                      .code(CODE_2)
                      .description(DESCRIPTION_2)
                      .build();
    }

    private JobTypeResponse generateJobTypeResponse1() {
        return JobTypeResponse.builder()
                              .code(CODE_1)
                              .description(DESCRIPTION_1)
                              .build();
    }

    private JobTypeResponse generateJobTypeResponse2() {
        return JobTypeResponse.builder()
                              .code(CODE_2)
                              .description(DESCRIPTION_2)
                              .build();
    }
}