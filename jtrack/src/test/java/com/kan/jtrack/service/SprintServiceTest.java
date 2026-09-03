package com.kan.jtrack.service;

import com.kan.jtrack.dto.request.AuditEntityRequest;
import com.kan.jtrack.dto.request.SprintRequest;
import com.kan.jtrack.dto.response.SprintResponse;
import com.kan.jtrack.entity.Sprint;
import com.kan.jtrack.exception.ResourceNotFoundException;
import com.kan.jtrack.exception.ValidationException;
import com.kan.jtrack.mapper.SprintMapper;
import com.kan.jtrack.repository.SprintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintServiceTest {

    private static final Integer ID_1 = 1;
    private static final String NAME_1 = "Sprint 1";
    private static final String STATUS_CODE_1 = "COMPLETED";
    private static final LocalDate START_DATE_1 = LocalDate.of(2026, 7, 1);
    private static final LocalDate END_DATE_1 = LocalDate.of(2026, 7, 22);
    private static final LocalDateTime CREATED_AT_1 = LocalDateTime.of(2026, 6, 15, 0, 0);
    private static final String CREATED_BY_1 = "user-1";
    private static final LocalDateTime UPDATED_AT_1 = LocalDateTime.of(2026, 7, 23, 0, 0);
    private static final String UPDATED_BY_1 = "user-2";

    private static final Integer ID_2 = 2;
    private static final String NAME_2 = "Sprint 2";
    private static final String STATUS_CODE_2 = "PLANNING";
    private static final LocalDate START_DATE_2 = LocalDate.of(2026, 7, 23);
    private static final LocalDate END_DATE_2 = LocalDate.of(2026, 8, 15);
    private static final LocalDateTime CREATED_AT_2 = LocalDateTime.of(2026, 7, 15, 0, 0);
    private static final String CREATED_BY_2 = "user-1";

    private static final Integer ID_UNKNOWN = 999;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private SprintMapper sprintMapper;

    @Mock
    private AuditEntityService auditEntityService;

    @InjectMocks
    private SprintService sprintService;

    // ============ getAll() Tests ============

    @Test
    void getAll_whenSprintsExist_shouldReturnSprintResponseList() {
        Sprint sprint1 = generateSprint1();
        Sprint sprint2 = generateSprint2();

        SprintResponse sprintResponse1 = generateSprintResponse1();
        SprintResponse sprintResponse2 = generateSprintResponse2();

        when(sprintRepository.findAll()).thenReturn(List.of(sprint1, sprint2));
        when(sprintMapper.toSprintResponse(sprint1)).thenReturn(sprintResponse1);
        when(sprintMapper.toSprintResponse(sprint2)).thenReturn(sprintResponse2);

        List<SprintResponse> result = sprintService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(sprintResponse1));
        assertTrue(result.contains(sprintResponse2));
    }

    @Test
    void getAll_whenNoSprintsExist_shouldReturnEmptyList() {
        when(sprintRepository.findAll()).thenReturn(List.of());

        List<SprintResponse> result = sprintService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ getById() Tests ============

    @ParameterizedTest
    @NullSource
    void getById_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> sprintService.getById(id));
    }

    @Test
    void getById_whenSprintExists_shouldReturnSprintResponse() {
        Sprint sprint = generateSprint1();
        SprintResponse sprintResponse = generateSprintResponse1();

        when(sprintRepository.findById(ID_1)).thenReturn(Optional.of(sprint));
        when(sprintMapper.toSprintResponse(sprint)).thenReturn(sprintResponse);

        SprintResponse result = sprintService.getById(ID_1);

        assertNotNull(result);
        assertEquals(sprintResponse, result);
    }

    @Test
    void getById_whenNoSprintExists_shouldThrowResourceNotFoundException() {
        when(sprintRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sprintService.getById(ID_UNKNOWN));
    }

    // ============ create() Tests ============

    @ParameterizedTest
    @NullSource
    void create_whenSprintRequestIsNull_shouldThrowValidationException(SprintRequest sprintRequest) {
        assertThrows(ValidationException.class, () -> sprintService.create(sprintRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenNameIsNullOrBlank_shouldThrowValidationException(String name) {
        SprintRequest sprintRequest = generateSprintRequest1();
        sprintRequest.setName(name);

        assertThrows(ValidationException.class, () -> sprintService.create(sprintRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void create_whenStatusCodeIsNullOrBlank_shouldThrowValidationException(String statusCode) {
        SprintRequest sprintRequest = generateSprintRequest1();
        sprintRequest.setStatusCode(statusCode);

        assertThrows(ValidationException.class, () -> sprintService.create(sprintRequest));
    }

    @Test
    void create_whenSprintRequestIsValid_shouldCreateAndReturnResponse() {
        SprintRequest sprintRequest = generateSprintRequest1();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Sprint sprint = generateSprint1();
        sprint.setId(null);
        Sprint savedSprint = generateSprint1();

        SprintResponse sprintResponse = generateSprintResponse1();

        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        when(sprintMapper.toSprint(sprintRequest, auditEntityRequest)).thenReturn(sprint);
        when(sprintRepository.save(sprint)).thenReturn(savedSprint);
        when(sprintMapper.toSprintResponse(savedSprint)).thenReturn(sprintResponse);

        SprintResponse result = sprintService.create(sprintRequest);

        assertNotNull(result);
        assertEquals(sprintResponse, result);

        verify(sprintRepository).save(sprint);
    }

    // ============ update() Tests ============

    @ParameterizedTest
    @NullSource
    void update_whenIdIsNull_shouldThrowValidationException(Integer id) {
        SprintRequest sprintRequest = generateSprintRequest1();
        assertThrows(ValidationException.class, () -> sprintService.update(id, sprintRequest));
    }

    @ParameterizedTest
    @NullSource
    void update_whenSprintRequestIsNull_shouldThrowValidationException(SprintRequest sprintRequest) {
        assertThrows(ValidationException.class, () -> sprintService.update(ID_1, sprintRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenNameIsNullOrBlank_shouldThrowValidationException(String name) {
        SprintRequest sprintRequest = generateSprintRequest1();
        sprintRequest.setName(name);

        assertThrows(ValidationException.class, () -> sprintService.update(ID_1, sprintRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void update_whenStatusCodeIsNullOrBlank_shouldThrowValidationException(String statusCode) {
        SprintRequest sprintRequest = generateSprintRequest1();
        sprintRequest.setStatusCode(statusCode);

        assertThrows(ValidationException.class, () -> sprintService.update(ID_1, sprintRequest));
    }

    @Test
    void update_whenNoSprintExists_shouldThrowResourceNotFoundException() {
        SprintRequest sprintRequest = generateSprintRequest1();
        when(sprintRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sprintService.update(ID_UNKNOWN, sprintRequest));
    }

    @Test
    void update_whenSprintExists_shouldUpdateAndReturnResponse() {
        SprintRequest sprintRequest = generateSprintRequest2();
        AuditEntityRequest auditEntityRequest = generateAuditEntityRequest();

        Sprint existingSprint = generateSprint2();
        Sprint savedSprint = generateSprint2();

        SprintResponse sprintResponse = generateSprintResponse2();

        when(sprintRepository.findById(ID_2)).thenReturn(Optional.of(existingSprint));
        when(auditEntityService.generateAuditEntityRequest()).thenReturn(auditEntityRequest);
        doNothing().when(sprintMapper).mapToSprint(existingSprint, sprintRequest, auditEntityRequest);
        when(sprintRepository.save(existingSprint)).thenReturn(savedSprint);
        when(sprintMapper.toSprintResponse(savedSprint)).thenReturn(sprintResponse);

        SprintResponse result = sprintService.update(ID_2, sprintRequest);

        assertNotNull(result);
        assertEquals(sprintResponse, result);

        verify(sprintMapper).mapToSprint(existingSprint, sprintRequest, auditEntityRequest);
        verify(sprintRepository).save(existingSprint);
    }

    // ============ delete() Tests ============

    @ParameterizedTest
    @NullSource
    void delete_whenIdIsNull_shouldThrowValidationException(Integer id) {
        assertThrows(ValidationException.class, () -> sprintService.delete(id));
    }

    @Test
    void delete_whenSprintExists_shouldDeleteSuccessfully() {
        Sprint existingSprint = generateSprint1();

        when(sprintRepository.findById(ID_1)).thenReturn(Optional.of(existingSprint));
        sprintService.delete(ID_1);

        verify(sprintRepository).delete(existingSprint);
    }

    @Test
    void delete_whenNoSprintExists_shouldThrowResourceNotFoundException() {
        when(sprintRepository.findById(ID_UNKNOWN)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sprintService.delete(ID_UNKNOWN));
    }

    // ============ Test data Creation Methods ============

    private Sprint generateSprint1() {
        return Sprint.builder()
                .id(ID_1)
                .name(NAME_1)
                .statusCode(STATUS_CODE_1)
                .startDate(START_DATE_1)
                .endDate(END_DATE_1)
                .createdAt(CREATED_AT_1)
                .createdBy(CREATED_BY_1)
                .updatedAt(UPDATED_AT_1)
                .updatedBy(UPDATED_BY_1)
                .build();
    }

    private Sprint generateSprint2() {
        return Sprint.builder()
                .id(ID_2)
                .name(NAME_2)
                .statusCode(STATUS_CODE_2)
                .startDate(START_DATE_2)
                .endDate(END_DATE_2)
                .createdAt(CREATED_AT_2)
                .createdBy(CREATED_BY_2)
                .build();
    }

    private SprintRequest generateSprintRequest1() {
        return SprintRequest.builder()
                .name(NAME_1)
                .statusCode(STATUS_CODE_1)
                .startDate(START_DATE_1)
                .endDate(END_DATE_1)
                .build();
    }

    private SprintRequest generateSprintRequest2() {
        return SprintRequest.builder()
                .name(NAME_2)
                .statusCode(STATUS_CODE_2)
                .startDate(START_DATE_2)
                .endDate(END_DATE_2)
                .build();
    }

    private AuditEntityRequest generateAuditEntityRequest() {
        return AuditEntityRequest.builder()
                                 .createdBy(CREATED_BY_1)
                                 .createdAt(CREATED_AT_1)
                                 .updatedBy(UPDATED_BY_1)
                                 .updatedAt(UPDATED_AT_1)
                                 .build();
    }

    private SprintResponse generateSprintResponse1() {
        return SprintResponse.builder()
                .id(ID_1)
                .name(NAME_1)
                .statusCode(STATUS_CODE_1)
                .startDate(START_DATE_1)
                .endDate(END_DATE_1)
                .createdAt(CREATED_AT_1)
                .createdBy(CREATED_BY_1)
                .updatedAt(UPDATED_AT_1)
                .updatedBy(UPDATED_BY_1)
                .build();
    }

    private SprintResponse generateSprintResponse2() {
        return SprintResponse.builder()
                .id(ID_2)
                .name(NAME_2)
                .statusCode(STATUS_CODE_2)
                .startDate(START_DATE_2)
                .endDate(END_DATE_2)
                .createdAt(CREATED_AT_2)
                .createdBy(CREATED_BY_2)
                .build();
    }
}