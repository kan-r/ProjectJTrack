import { ComponentFixture, TestBed } from '@angular/core/testing';
import { convertToParamMap, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { JobForm } from './job-form';
import { JobService } from '../../job-service/job-service';
import { SprintService } from '../../../sprint/sprint-service/sprint-service';
import { UserService } from '../../../user/user-service/user-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';

const mockJobs = [
  {
    id: 1,
    name: 'Parent Job',
    description: 'Top level job',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'high',
    priorityDescription: 'High',
    statusCode: 'todo',
    statusDescription: 'To Do',
    assignedTo: 'user-1',
    assignedToName: 'Alice',
    estimatedHours: 5,
    actualHours: 0,
    sprintId: 10,
    sprintName: 'Sprint 10',
    parentId: null,
    parentName: '',
  },
  {
    id: 2,
    name: 'Child Job',
    description: 'Sub task',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'medium',
    priorityDescription: 'Medium',
    statusCode: 'inprogress',
    statusDescription: 'In Progress',
    assignedTo: 'user-2',
    assignedToName: 'Bob',
    estimatedHours: 3,
    actualHours: 1,
    sprintId: 10,
    sprintName: 'Sprint 10',
    parentId: 1,
    parentName: 'Parent Job',
  },
  {
    id: 3,
    name: 'Other Parent',
    description: 'Another top level task',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'low',
    priorityDescription: 'Low',
    statusCode: 'done',
    statusDescription: 'Done',
    assignedTo: 'user-3',
    assignedToName: 'Charlie',
    estimatedHours: 2,
    actualHours: 2,
    sprintId: 11,
    sprintName: 'Sprint 11',
    parentId: null,
    parentName: '',
  },
];

const mockJob = {
  id: 99,
  name: 'Existing Job',
  description: 'Loaded from server',
  typeCode: 'task',
  typeDescription: 'Task',
  priorityCode: 'medium',
  priorityDescription: 'Medium',
  statusCode: 'todo',
  statusDescription: 'To Do',
  assignedTo: 'user-1',
  assignedToName: 'Alice',
  estimatedHours: 7,
  actualHours: 2,
  sprintId: 10,
  sprintName: 'Sprint 10',
  parentId: 1,
  parentName: 'Parent Job',
};

describe('JobForm', () => {
  let component: JobForm;
  let fixture: ComponentFixture<JobForm>;
  let routeMock: { snapshot: { paramMap: any } };

  let jobService: {
    getJobs: ReturnType<typeof vi.fn>;
    getJobTypes: ReturnType<typeof vi.fn>;
    getJobStatuses: ReturnType<typeof vi.fn>;
    getJobPriorities: ReturnType<typeof vi.fn>;
    getJobById: ReturnType<typeof vi.fn>;
    createJob: ReturnType<typeof vi.fn>;
    updateJob: ReturnType<typeof vi.fn>;
  };

  let sprintService: { getSprints: ReturnType<typeof vi.fn> };
  let userService: { getUsers: ReturnType<typeof vi.fn> };
  let notificationService: { notifySuccess: ReturnType<typeof vi.fn> };
  let locationMock: { back: ReturnType<typeof vi.fn> };

  const createComponent = async () => {
    await TestBed.configureTestingModule({
      imports: [JobForm],
      providers: [
        { provide: JobService, useValue: jobService },
        { provide: SprintService, useValue: sprintService },
        { provide: UserService, useValue: userService },
        { provide: NotificationService, useValue: notificationService },
        { provide: ActivatedRoute, useValue: routeMock },
        { provide: Location, useValue: locationMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(JobForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  };

  beforeEach(async () => {
    routeMock = { snapshot: { paramMap: convertToParamMap({}) } };

    jobService = {
      getJobs: vi.fn(() => ({ value: () => mockJobs, hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
      getJobTypes: vi.fn(() => ({ value: () => [{ code: 'task', description: 'Task' }], hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
      getJobStatuses: vi.fn(() => ({ value: () => [{ code: 'todo', description: 'To Do' }], hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
      getJobPriorities: vi.fn(() => ({ value: () => [{ code: 'high', description: 'High' }], hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
      getJobById: vi.fn(() => ({ value: () => mockJob, hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
      createJob: vi.fn(() => ({ subscribe: (handlers: any) => { handlers.next?.({ id: 100 }); return { unsubscribe: () => {} }; } })),
      updateJob: vi.fn(() => ({ subscribe: (handlers: any) => { handlers.next?.({ id: 99 }); return { unsubscribe: () => {} }; } })),
    };

    sprintService = {
      getSprints: vi.fn(() => ({ value: () => [{ id: 10, name: 'Sprint 10' }], hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
    };

    userService = {
      getUsers: vi.fn(() => ({ value: () => [{ id: 'user-1', name: 'Alice' }], hasValue: () => true, isLoading: vi.fn(() => false), reload: vi.fn() })),
    };

    notificationService = {
      notifySuccess: vi.fn(),
    };

    locationMock = {
      back: vi.fn(),
    };

    await createComponent();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should keep create mode by default and show create title', () => {
    expect(component.isEditMode).toBe(false);
    expect(component.pageTitle).toBe('Create Job');
  });

  it('should filter parent jobs to exclude the current job and non-root jobs', () => {
    component.jobId.set(1);
    const parents = component.parentJobs();

    expect(parents.map((job) => job.id)).toEqual([3]);
  });

  it('should populate the form when editing an existing job', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '99' });
    component.ngOnInit();

    expect(component.isEditMode).toBe(true);
    expect(component.pageTitle).toBe('Edit Job');
    expect(component.jobForm.get('name')?.value).toBe('Existing Job');
    expect(component.jobForm.get('sprintId')?.value).toBe(10);
    expect(component.jobForm.get('parentId')?.value).toBe(1);
  });

  it('should create a new job when the form is valid', () => {
    component.jobForm.patchValue({
      sprintId: 10,
      name: 'New Job',
      description: 'Fresh task',
      typeCode: 'task',
      statusCode: 'todo',
      priorityCode: 'high',
      assignedTo: 'user-1',
      estimatedHours: 4,
      actualHours: 0,
      parentId: null,
    });

    component.onSubmit();

    expect(jobService.createJob).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Job created successfully.');
    expect(locationMock.back).toHaveBeenCalledTimes(1);
  });

  it('should update an existing job when in edit mode', () => {
    routeMock.snapshot.paramMap = convertToParamMap({ id: '99' });
    component.ngOnInit();

    component.jobForm.patchValue({
      name: 'Updated Title',
      description: 'Updated description',
      typeCode: 'task',
      statusCode: 'done',
      priorityCode: 'low',
    });

    component.onSubmit();

    expect(jobService.updateJob).toHaveBeenCalledTimes(1);
    expect(notificationService.notifySuccess).toHaveBeenCalledWith('Job updated successfully.');
    expect(locationMock.back).toHaveBeenCalledTimes(1);
  });

  it('should not submit invalid form values', () => {
    component.jobForm.patchValue({
      name: '',
    });

    component.onSubmit();

    expect(jobService.createJob).not.toHaveBeenCalled();
    expect(jobService.updateJob).not.toHaveBeenCalled();
  });
});
