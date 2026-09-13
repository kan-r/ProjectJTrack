import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { convertToParamMap } from '@angular/router';
import { SprintForm } from './sprint-form';
import { SprintService } from '../../sprint-service/sprint-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';

const mockSprint = {
  id: 10,
  name: 'Sprint 10',
  statusCode: 'active',
  statusDescription: 'Active',
  startDate: '2024-01-01',
  endDate: '2024-01-15',
};

describe('SprintForm', () => {
  let component: SprintForm;
  let routeMock: { snapshot: { paramMap: any } };

  let sprintService: {
    getSprintStatuses: ReturnType<typeof vi.fn>;
    getSprintById: ReturnType<typeof vi.fn>;
    createSprint: ReturnType<typeof vi.fn>;
    updateSprint: ReturnType<typeof vi.fn>;
  };

  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    routeMock = { snapshot: { paramMap: convertToParamMap({}) } };

    sprintService = {
      getSprintStatuses: vi.fn(() => ({
        value: () => [{ code: 'active', description: 'Active' }],
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),

      getSprintById: vi.fn(() => ({
        value: () => mockSprint,
        hasValue: () => true,
        isLoading: () => false,
        reload: vi.fn(),
      })),

      createSprint: vi.fn(() => ({
        subscribe: (handlers: any) => {
          handlers.next?.({ ...mockSprint, id: 11 });
          return { unsubscribe: () => undefined };
        },
      })),

      updateSprint: vi.fn(() => ({
        subscribe: (handlers: any) => {
          handlers.next?.({ ...mockSprint, id: 10 });
          return { unsubscribe: () => undefined };
        },
      })),
    };

    notificationService = {
      notifySuccess: vi.fn(),
    };

    router = {
      navigate: vi.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: SprintService, useValue: sprintService },
        { provide: NotificationService, useValue: notificationService },
        { provide: ActivatedRoute, useValue: routeMock },
        { provide: Router, useValue: router },
      ],
    });

    component = TestBed.runInInjectionContext(() => new SprintForm());
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should stay in create mode by default', () => {
    expect(component.isEditMode).toBe(false);
    expect(component.pageTitle).toBe('Create Sprint');
  });

  it('should enter edit mode when a route id is present', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '10' });
    component.ngOnInit();

    expect(component.isEditMode).toBe(true);
    expect(component.pageTitle).toBe('Edit Sprint');
    expect(component['sprintId']()).toBe(10);
  });

  it('should create a sprint when the form is valid', () => {
    component.sprintForm.patchValue({
      name: 'Sprint 11',
      statusCode: 'planned',
      startDate: new Date('2024-02-01'),
      endDate: new Date('2024-02-15'),
    });

    component.onSubmit();

    expect(sprintService.createSprint).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Sprint created successfully.');
    expect(router.navigate).toHaveBeenCalledWith(['/sprints']);
  });

  it('should update a sprint when in edit mode', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '10' });
    component.ngOnInit();
    
    component.sprintForm.patchValue({
      name: 'Updated Sprint',
      statusCode: 'archived',
      startDate: new Date('2024-03-01'),
      endDate: new Date('2024-03-20'),
    });

    component.onSubmit();

    expect(sprintService.updateSprint).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Sprint updated successfully.');
    expect(router.navigate).toHaveBeenCalledWith(['/sprints']);
  });

  it('should not submit an invalid sprint form', () => {
    component.sprintForm.patchValue({
      name: '',
      statusCode: 'active',
      startDate: new Date('2024-01-01'),
      endDate: new Date('2024-01-15'),
    });

    component.onSubmit();

    expect(sprintService.createSprint).not.toHaveBeenCalled();
    expect(sprintService.updateSprint).not.toHaveBeenCalled();
  });
});
