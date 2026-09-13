import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { provideNativeDateAdapter } from '@angular/material/core';
import { TimesheetForm } from './timesheet-form';
import { TimesheetService } from '../../timesheet-service/timesheet-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { UserService } from '../../../user/user-service/user-service';
import { JobService } from '../../../job/job-service/job-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';

const mockUsers = [
  { id: 'user-1', fullName: 'Alice Admin' },
  { id: 'user-2', fullName: 'Bob Manager' },
];

const mockJobs = [
  { id: 10, name: 'Job 10' },
  { id: 11, name: 'Job 11' },
];

const mockTimesheet = {
  id: 42,
  userId: 'user-1',
  userName: 'Alice Admin',
  jobId: 10,
  jobName: 'Job 10',
  workedDate: '2024-02-15',
  workedHours: 8,
};

describe('TimesheetForm', () => {
  let component: TimesheetForm;
  let routeMock: { snapshot: { paramMap: any } };

  let timesheetService: {
    getTimesheetById: ReturnType<typeof vi.fn>;
    createTimesheet: ReturnType<typeof vi.fn>;
    updateTimesheet: ReturnType<typeof vi.fn>;
  };

  let userService: { getUsers: ReturnType<typeof vi.fn> };
  let jobService: { getJobs: ReturnType<typeof vi.fn> };
  let authService: { currentUser: ReturnType<typeof vi.fn> };
  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    routeMock = { snapshot: { paramMap: convertToParamMap({}) } };

    timesheetService = {
      getTimesheetById: vi.fn((idSignal) => ({
        value: () => (idSignal() !== null && idSignal() !== undefined ? mockTimesheet : null),
        hasValue: () => idSignal() !== null && idSignal() !== undefined,
        isLoading: () => false,
        reload: vi.fn(),
      })),

      createTimesheet: vi.fn(() => ({
        subscribe: (handlers: any) => {
          handlers.next?.({ ...mockTimesheet, id: 99 });
          return { unsubscribe: () => undefined };
        },
      })),
      
      updateTimesheet: vi.fn(() => ({
        subscribe: (handlers: any) => {
          handlers.next?.({ ...mockTimesheet, id: 42 });
          return { unsubscribe: () => undefined };
        },
      })),
    };

    userService = {
      getUsers: vi.fn(() => ({
        value: () => mockUsers,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),
    };

    jobService = {
      getJobs: vi.fn(() => ({
        value: () => mockJobs,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),
    };

    authService = {
      currentUser: vi.fn(() => ({ id: 'user-1', fullName: 'Alice Admin' })),
    };

    notificationService = {
      notifySuccess: vi.fn(),
    };

    router = {
      navigate: vi.fn(),
    };

    TestBed.configureTestingModule({
      imports: [TimesheetForm],
      providers: [
        provideNativeDateAdapter(),
        { provide: TimesheetService, useValue: timesheetService },
        { provide: UserService, useValue: userService },
        { provide: JobService, useValue: jobService },
        { provide: AuthService, useValue: authService },
        { provide: NotificationService, useValue: notificationService },
        { provide: ActivatedRoute, useValue: routeMock },
        { provide: Router, useValue: router },
      ],
    });

    component = TestBed.createComponent(TimesheetForm).componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should stay in create mode by default and prefill the current user', () => {
    component.ngOnInit();

    expect(component.isEditMode).toBe(false);
    expect(component.pageTitle).toBe('Create Timesheet');
    expect(component.timesheetForm.get('userId')?.value).toBe('user-1');
  });

  it('should enter edit mode when a route id is present', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '42' });

    component.ngOnInit();

    expect(component.isEditMode).toBe(true);
    expect(component.pageTitle).toBe('Edit Timesheet');
    expect(component['timesheetId']()).toBe(42);
    expect(component.timesheetForm.get('id')?.value).toBeNull();
  });

  it('should create a timesheet when the form is valid', () => {
    component.timesheetForm.patchValue({
      userId: 'user-1',
      jobId: 10,
      workedDate: new Date('2024-02-15'),
      workedHours: 8,
    });

    component.onSubmit();

    expect(timesheetService.createTimesheet).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Timesheet created successfully.');
    expect(router.navigate).toHaveBeenCalledWith(['/timesheets']);
  });

  it('should update a timesheet when in edit mode', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '42' });
    component.ngOnInit();

    component.timesheetForm.patchValue({
      userId: 'user-1',
      jobId: 11,
      workedDate: new Date('2024-03-10'),
      workedHours: 6,
    });

    component.onSubmit();

    expect(timesheetService.updateTimesheet).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Timesheet updated successfully.');
    expect(router.navigate).toHaveBeenCalledWith(['/timesheets']);
  });

  it('should not submit when the timesheet form is invalid', () => {
    component.timesheetForm.patchValue({
      userId: '',
      jobId: 10,
      workedDate: new Date('2024-02-15'),
      workedHours: 8,
    });

    component.onSubmit();

    expect(timesheetService.createTimesheet).not.toHaveBeenCalled();
    expect(timesheetService.updateTimesheet).not.toHaveBeenCalled();
  });
});
