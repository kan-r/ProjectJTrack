import { signal } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { SprintService } from './sprint-service';

const apiUrl = 'http://localhost:8282';

const mockSprints = [
  {
    id: 1,
    name: 'Sprint 1',
    statusCode: 'active',
    statusDescription: 'Active',
    startDate: '2026-09-01',
    endDate: '2026-09-14',
  },
];

describe('SprintService', () => {
  let service: SprintService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SprintService, provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(SprintService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should expose SprintStatus resource accessor', () => {
    const sprintStatusesResource = TestBed.runInInjectionContext(() => service.getSprintStatuses());

    expect(sprintStatusesResource).toBeTruthy();
    expect(typeof sprintStatusesResource.reload).toBe('function');
  });

  it('should expose Sprints resource accessor', () => {
    const sprintsResource = TestBed.runInInjectionContext(() => service.getSprints());

    expect(sprintsResource).toBeTruthy();
    expect(typeof sprintsResource.reload).toBe('function');
  });

  it('should expose SprintById resource accessor', () => {
    const id = signal<number | null>(7);
    const sprintResource = TestBed.runInInjectionContext(() => service.getSprintById(id));

    expect(sprintResource).toBeTruthy();
    expect(typeof sprintResource.reload).toBe('function');
  });

  it('should create a new sprint with a POST request', () => {
    const payload = mockSprints[0];

    service.createSprint(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/sprints`);
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should update an existing sprint with a PUT request', () => {
    const payload = { ...mockSprints[0], name: 'Sprint 1 updated' };

    service.updateSprint(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/sprints/1`);
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should delete a sprint with a DELETE request', () => {
    service.deleteSprint(1).subscribe((response) => {
      expect(response).toBeNull();
    });

    const request = httpMock.expectOne(`${apiUrl}/sprints/1`);
    expect(request.request.method).toBe('DELETE');
    request.flush(null);
  });
});
