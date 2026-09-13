import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Sprints } from './sprints';
import { SprintService } from '../../sprint-service/sprint-service';
import { ConfirmationService } from '../../../../shared/confirmation/confirmation-service/confirmation-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';

const mockSprints = [
  {
    id: 1,
    name: 'Sprint 1',
    statusCode: 'active',
    statusDescription: 'Active',
    startDate: '2024-01-01',
    endDate: '2024-01-15',
  },
  {
    id: 2,
    name: 'Sprint 2',
    statusCode: 'planned',
    statusDescription: 'Planned',
    startDate: '2024-01-16',
    endDate: '2024-01-31',
  },
];

describe('Sprints', () => {
  let component: Sprints;

  let sprintService: {
    getSprints: ReturnType<typeof vi.fn>;
    deleteSprint: ReturnType<typeof vi.fn>;
  };

  let confirmationService: { confirm: ReturnType<typeof vi.fn> };
  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let authService: { hasAnyRole: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    sprintService = {
      getSprints: vi.fn(() => ({
        value: () => mockSprints,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),

      deleteSprint: vi.fn(() => ({
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
        { provide: SprintService, useValue: sprintService },
        { provide: ConfirmationService, useValue: confirmationService },
        { provide: NotificationService, useValue: notificationService },
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router },
      ],
    });

    component = TestBed.runInInjectionContext(() => new Sprints());
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should expose create and edit access based on roles', () => {
    expect(component.canCreate).toBe(true);
    expect(component.canEdit).toBe(true);
    expect(authService.hasAnyRole).toHaveBeenCalledWith(['admin', 'manager']);
  });

  it('should expose the visible sprint table columns', () => {
    expect(component.displayedColumns).toContain('name');
    expect(component.displayedColumns).toContain('statusDescription');
    expect(component.displayedColumns).toContain('startDate');
    expect(component.displayedColumns).toContain('edit');
  });

  it('should navigate to the edit page when an edit action is clicked', () => {
    component.onActionClick('edit', 2);
    expect(router.navigate).toHaveBeenCalledWith(['/sprints/edit', 2]);
  });

  it('should confirm and delete the selected sprint when confirmed', () => {
    const reload = vi.fn();
    component.sprintResource = { ...component.sprintResource, reload } as any;

    component.onActionClick('delete', 1);

    expect(confirmationService.confirm).toHaveBeenCalledWith('Delete Sprint for ID: 1', 'Are you sure you want to delete?');
    expect(sprintService.deleteSprint).toHaveBeenCalledWith(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Sprint deleted successfully.');
    expect(reload).toHaveBeenCalledTimes(1);
  });

  it('should not delete the selected sprint when confirmation is cancelled', () => {
    confirmationService.confirm = vi.fn(() => ({
      subscribe: (handler: (value: boolean) => void) => handler(false),
    }));

    component.onActionClick('delete', 5);

    expect(sprintService.deleteSprint).not.toHaveBeenCalled();
  });
});
