package com.kan.jtrack.service;

import com.kan.jtrack.dto.response.SprintStatusResponse;
import com.kan.jtrack.entity.SprintStatus;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.SprintStatusMapper;
import com.kan.jtrack.repository.SprintStatusRepository;
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
class SprintStatusServiceTest {

    private static final String CODE_1 = "PLANNING";
    private static final String DESCRIPTION_1 = "Planning";
    private static final Integer DISPLAY_ORDER_1 = 1;

    private static final String CODE_2 = "ACTIVE";
    private static final String DESCRIPTION_2 = "Active";
    private static final Integer DISPLAY_ORDER_2 = 2;

    private static final String CODE_UNKNOWN = "UNKNOWN";

    @Mock
    private SprintStatusRepository sprintStatusRepository;

    @Mock
    private SprintStatusMapper sprintStatusMapper;

    @InjectMocks
    private SprintStatusService sprintStatusService;


    // ============ getAll() Tests ============

    @Test
    void getAll_whenSprintStatusesExist_shouldReturnSprintStatusResponseList() {
        SprintStatus sprintStatus1 = generateSprintStatus1();
        SprintStatus sprintStatus2 = generateSprintStatus2();

        SprintStatusResponse sprintStatusResponse1 = generateSprintStatusResponse1();
        SprintStatusResponse sprintStatusResponse2 = generateSprintStatusResponse2();

        when(sprintStatusRepository.findAll()).thenReturn(List.of(sprintStatus2, sprintStatus1));
        when(sprintStatusMapper.toSprintStatusResponse(sprintStatus1)).thenReturn(sprintStatusResponse1);
        when(sprintStatusMapper.toSprintStatusResponse(sprintStatus2)).thenReturn(sprintStatusResponse2);

        List<SprintStatusResponse> result = sprintStatusService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(sprintStatusResponse1, result.get(0));
        assertEquals(sprintStatusResponse2, result.get(1));
    }

    @Test
    void getAll_whenNoSprintStatusesExist_shouldReturnEmptyList() {
        when(sprintStatusRepository.findAll()).thenReturn(List.of());

        List<SprintStatusResponse> result = sprintStatusService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> sprintStatusService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void getById_whenIdIsBlank_shouldThrowValidationException(String id) {
        assertThrows(ValidationException.class, () -> sprintStatusService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACTIVE", "active", "Active"})
    void getById_whenIdHasDifferentCase_shouldReturnSprintStatusResponse(String id) {
        SprintStatus sprintStatus = generateSprintStatus1();
        SprintStatusResponse sprintStatusResponse = generateSprintStatusResponse1();

        when(sprintStatusRepository.findById(id.toUpperCase())).thenReturn(of(sprintStatus));
        when(sprintStatusMapper.toSprintStatusResponse(sprintStatus)).thenReturn(sprintStatusResponse);

        SprintStatusResponse result = sprintStatusService.getById(id);

        assertNotNull(result);
        assertEquals(sprintStatusResponse, result);
    }

    @Test
    void getById_whenNoSprintStatusExists_shouldThrowResourceNotFoundException() {
        when(sprintStatusRepository.findById(CODE_UNKNOWN)).thenReturn(empty());
        assertThrows(ResourceNotFoundException.class, () -> sprintStatusService.getById(CODE_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private SprintStatus generateSprintStatus1() {
        return SprintStatus.builder()
                .code(CODE_1)
                .description(DESCRIPTION_1)
                .displayOrder(DISPLAY_ORDER_1)
                .build();
    }

    private SprintStatus generateSprintStatus2() {
        return SprintStatus.builder()
                .code(CODE_2)
                .description(DESCRIPTION_2)
                .displayOrder(DISPLAY_ORDER_2)
                .build();
    }

    private SprintStatusResponse generateSprintStatusResponse1() {
        return SprintStatusResponse.builder()
                .code(CODE_1)
                .description(DESCRIPTION_1)
                .displayOrder(DISPLAY_ORDER_1)
                .build();
    }

    private SprintStatusResponse generateSprintStatusResponse2() {
        return SprintStatusResponse.builder()
                .code(CODE_2)
                .description(DESCRIPTION_2)
                .displayOrder(DISPLAY_ORDER_2)
                .build();
    }
}