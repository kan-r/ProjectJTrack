import { signal } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { JobService } from './job-service';

const apiUrl = 'http://localhost:8282';

const mockJobs = [
  {
    id: 1,
    name: 'Write tests',
    description: 'Add coverage',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'medium',
    priorityDescription: 'Medium',
    statusCode: 'todo',
    statusDescription: 'To Do',
    assignedTo: 'user-1',
    assignedToName: 'Alice',
    estimatedHours: 3,
    actualHours: 0,
    sprintId: 1,
    sprintName: 'Sprint 1',
    parentId: 0,
    parentName: '',
  },
];

describe('JobService', () => {
  let service: JobService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JobService, provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(JobService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should expose JobPriority resource accessor', () => {
    const jobPrioritiesResource = TestBed.runInInjectionContext(() => service.getJobPriorities());

    expect(jobPrioritiesResource).toBeTruthy();
    expect(typeof jobPrioritiesResource.reload).toBe('function');
  });

  it('should expose JobStatus resource accessor', () => {
    const jobStatusesResource = TestBed.runInInjectionContext(() => service.getJobStatuses());

    expect(jobStatusesResource).toBeTruthy();
    expect(typeof jobStatusesResource.reload).toBe('function');
  });

  it('should expose JobType resource accessor', () => {
    const jobTypesResource = TestBed.runInInjectionContext(() => service.getJobTypes());

    expect(jobTypesResource).toBeTruthy();
    expect(typeof jobTypesResource.reload).toBe('function');
  });

  it('should expose Jobs resource accessor', () => {
    const jobsResource = TestBed.runInInjectionContext(() => service.getJobs());

    expect(jobsResource).toBeTruthy();
    expect(typeof jobsResource.reload).toBe('function');
  });

  it('should expose JobById resource accessor', () => {
    const id = signal<number | null>(7);
    const jobResource = TestBed.runInInjectionContext(() => service.getJobById(id));

    expect(jobResource).toBeTruthy();
    expect(typeof jobResource.reload).toBe('function');
  });

  it('should create a new job with a POST request', () => {
    const payload = mockJobs[0];

    service.createJob(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/jobs`);
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should update an existing job with a PUT request', () => {
    const payload = { ...mockJobs[0], name: 'Updated job' };

    service.updateJob(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/jobs/1`);
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should update a job status with a PATCH request', () => {
    service.updateJobStatus(1, 'done').subscribe((response) => {
      expect(response).toEqual({ ...mockJobs[0], statusCode: 'done' });
    });

    const request = httpMock.expectOne(`${apiUrl}/jobs/1/status`);
    expect(request.request.method).toBe('PATCH');
    expect(request.request.body).toEqual({ statusCode: 'done' });
    request.flush({ ...mockJobs[0], statusCode: 'done' });
  });

  it('should delete a job with a DELETE request', () => {
    service.deleteJob(1).subscribe((response) => {
      expect(response).toBeNull();
    });

    const request = httpMock.expectOne(`${apiUrl}/jobs/1`);
    expect(request.request.method).toBe('DELETE');
    request.flush(null);
  });
});
