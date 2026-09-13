import { signal } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TimesheetService } from './timesheet-service';

const apiUrl = 'http://localhost:8282';

const mockTimesheets = [
  {
    id: 1,
    userId: 'user-1',
    userName: 'Alice Admin',
    jobId: 10,
    jobName: 'Job 10',
    workedDate: '2026-09-01',
    workedHours: 7,
  },
];

describe('TimesheetService', () => {
  let service: TimesheetService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TimesheetService, provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(TimesheetService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should expose Timesheets resource accessor', () => {
    const timesheetsResource = TestBed.runInInjectionContext(() => service.getTimesheets());

    expect(timesheetsResource).toBeTruthy();
    expect(typeof timesheetsResource.reload).toBe('function');
  });

  it('should expose TimesheetById resource accessor', () => {
    const id = signal<number | null>(7);
    const timesheetResource = TestBed.runInInjectionContext(() => service.getTimesheetById(id));

    expect(timesheetResource).toBeTruthy();
    expect(typeof timesheetResource.reload).toBe('function');
  });

  it('should create a new timesheet with a POST request', () => {
    const payload = mockTimesheets[0];

    service.createTimesheet(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/timesheets`);

    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should update an existing timesheet with a PUT request', () => {
    const payload = { ...mockTimesheets[0], workedHours: 8 };

    service.updateTimesheet(payload).subscribe((response) => {
      expect(response).toEqual(payload);
    });

    const request = httpMock.expectOne(`${apiUrl}/timesheets/1`);
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual(payload);
    request.flush(payload);
  });

  it('should delete a timesheet with a DELETE request', () => {
    service.deleteTimesheet(1).subscribe((response) => {
      expect(response).toBeNull();
    });

    const request = httpMock.expectOne(`${apiUrl}/timesheets/1`);
    
    expect(request.request.method).toBe('DELETE');
    request.flush(null);
  });
});
