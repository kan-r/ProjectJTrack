import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Timesheets } from './timesheets';
import { TimesheetService } from '../../timesheet-service/timesheet-service';
import { ConfirmationService } from '../../../../shared/confirmation/confirmation-service/confirmation-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';

const mockTimesheets = [
  {
    id: 1,
    userId: 'user-1',
    userName: 'Alice Admin',
    jobId: 10,
    jobName: 'Job 10',
    workedDate: '2024-02-01',
    workedHours: 7,
  },
  {
    id: 2,
    userId: 'user-2',
    userName: 'Bob Manager',
    jobId: 11,
    jobName: 'Job 11',
    workedDate: '2024-02-02',
    workedHours: 5,
  },
];

describe('Timesheets', () => {
  let component: Timesheets;

  let timesheetService: {
    getTimesheets: ReturnType<typeof vi.fn>;
    deleteTimesheet: ReturnType<typeof vi.fn>;
  };

  let confirmationService: { confirm: ReturnType<typeof vi.fn> };
  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let authService: { hasAnyRole: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    timesheetService = {
      getTimesheets: vi.fn(() => ({
        value: () => mockTimesheets,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),

      deleteTimesheet: vi.fn(() => ({
        subscribe: (handlers: any) => {
          handlers.next?.();
          return { unsubscribe: () => undefined };
        },
      })),
    };

    confirmationService = {
      confirm: vi.fn(() => ({
        subscribe: (handler: (value: boolean) => void) => handler(true),
      })),
    };

    notificationService = {
      notifySuccess: vi.fn(),
    };

    authService = {
      hasAnyRole: vi.fn(() => true),
    };

    router = {
      navigate: vi.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: TimesheetService, useValue: timesheetService },
        { provide: ConfirmationService, useValue: confirmationService },
        { provide: NotificationService, useValue: notificationService },
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router },
      ],
    });

    component = TestBed.runInInjectionContext(() => new Timesheets());
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should expose create and edit access based on roles', () => {
    expect(component.canCreate).toBe(true);
    expect(component.canEdit).toBe(true);
    expect(authService.hasAnyRole).toHaveBeenCalledWith(['admin', 'manager', 'user']);
  });

  it('should expose the visible table columns', () => {
    expect(component.displayedColumns).toContain('userName');
    expect(component.displayedColumns).toContain('jobName');
    expect(component.displayedColumns).toContain('workedDate');
    expect(component.displayedColumns).toContain('edit');
    expect(component.displayedColumns).not.toContain('userId');
  });

  it('should navigate to the edit page when an edit action is clicked', () => {
    component.onActionClick('edit', 2);
    expect(router.navigate).toHaveBeenCalledWith(['/timesheets/edit', 2]);
  });

  it('should confirm and delete the selected timesheet when confirmed', () => {
    const reload = vi.fn();
    component.timesheetResource = { ...component.timesheetResource, reload } as any;

    component.onActionClick('delete', 1);

    expect(confirmationService.confirm).toHaveBeenCalledWith(
      'Delete Timesheet for ID: 1',
      'Are you sure you want to delete?'
    );
    
    expect(timesheetService.deleteTimesheet).toHaveBeenCalledWith(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Timesheet deleted successfully.');
    expect(reload).toHaveBeenCalledTimes(1);
  });

  it('should not delete the selected timesheet when confirmation is cancelled', () => {
    confirmationService.confirm = vi.fn(() => ({
      subscribe: (handler: (value: boolean) => void) => handler(false),
    }));

    component.onActionClick('delete', 5);

    expect(timesheetService.deleteTimesheet).not.toHaveBeenCalled();
  });
});
