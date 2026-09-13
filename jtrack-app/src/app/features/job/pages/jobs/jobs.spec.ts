import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Jobs } from './jobs';
import { JobService } from '../../job-service/job-service';
import { ConfirmationService } from '../../../../shared/confirmation/confirmation-service/confirmation-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';

const mockJobs = [
  {
    id: 1,
    name: 'Write tests',
    description: 'Add Jobs tests',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'high',
    priorityDescription: 'High',
    statusCode: 'todo',
    statusDescription: 'To Do',
    assignedTo: 'user-1',
    assignedToName: 'Alice',
    estimatedHours: 3,
    actualHours: 0,
    sprintId: 10,
    sprintName: 'Sprint 10',
    parentId: null,
    parentName: '',
  },
  {
    id: 2,
    name: 'Ship feature',
    description: 'Release candidate',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'medium',
    priorityDescription: 'Medium',
    statusCode: 'done',
    statusDescription: 'Done',
    assignedTo: 'user-2',
    assignedToName: 'Bob',
    estimatedHours: 5,
    actualHours: 5,
    sprintId: 11,
    sprintName: 'Sprint 11',
    parentId: null,
    parentName: '',
  },
];

describe('Jobs', () => {
  let component: Jobs;

  let jobService: {
    getJobs: ReturnType<typeof vi.fn>;
    deleteJob: ReturnType<typeof vi.fn>;
  };

  let confirmationService: { confirm: ReturnType<typeof vi.fn> };
  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let authService: { hasAnyRole: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    jobService = {
      getJobs: vi.fn(() => ({
        value: () => mockJobs,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),
      
      deleteJob: vi.fn(() => ({
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
        { provide: JobService, useValue: jobService },
        { provide: ConfirmationService, useValue: confirmationService },
        { provide: NotificationService, useValue: notificationService },
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router },
      ],
    });

    component = TestBed.runInInjectionContext(() => new Jobs());
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should expose create and edit access based on roles', () => {
    expect(component.canCreate).toBe(true);
    expect(component.canEdit).toBe(true);
    expect(authService.hasAnyRole).toHaveBeenCalledWith(['admin', 'manager']);
  });

  it('should expose the visible table columns', () => {
    expect(component.displayedColumns).toContain('name');
    expect(component.displayedColumns).toContain('statusDescription');
    expect(component.displayedColumns).toContain('edit');
    expect(component.displayedColumns).not.toContain('assignedTo');
  });

  it('should navigate to the edit page when an edit action is clicked', () => {
    component.onActionClick('edit', 2);

    expect(router.navigate).toHaveBeenCalledWith(['/jobs/edit', 2]);
  });

  it('should confirm and delete the selected job when confirmed', () => {
    const reload = vi.fn();
    component.jobResource = { ...component.jobResource, reload } as any;

    component.onActionClick('delete', 1);

    expect(confirmationService.confirm).toHaveBeenCalledWith('Delete Job for ID: 1', 'Are you sure you want to delete?');
    expect(jobService.deleteJob).toHaveBeenCalledWith(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Sprint deleted successfully.');
    expect(reload).toHaveBeenCalledTimes(1);
  });

  it('should not delete the selected job when confirmation is cancelled', () => {
    confirmationService.confirm = vi.fn(() => ({
      subscribe: (handler: (value: boolean) => void) => handler(false),
    }));

    component.onActionClick('delete', 5);

    expect(jobService.deleteJob).not.toHaveBeenCalled();
  });
});
