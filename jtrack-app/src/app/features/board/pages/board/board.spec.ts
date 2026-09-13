import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';
import { of } from 'rxjs';
import { Board } from './board';
import { JobService } from '../../../job/job-service/job-service';
import { SprintService } from '../../../sprint/sprint-service/sprint-service';

const mockStatuses = [
  { code: 'todo', description: 'To Do', displayOrder: 1 },
  { code: 'inprogress', description: 'In Progress', displayOrder: 2 },
  { code: 'done', description: 'Done', displayOrder: 3 },
];

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
    statusCode: 'upcoming',
    statusDescription: 'Upcoming',
    startDate: '2024-01-16',
    endDate: '2024-01-31',
  },
];

const mockJobs = [
  {
    id: 1,
    name: 'Write tests',
    description: 'Add board unit tests',
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
  {
    id: 2,
    name: 'Refine board filter',
    description: 'Adjust sprint filtering',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'high',
    priorityDescription: 'High',
    statusCode: 'inprogress',
    statusDescription: 'In Progress',
    assignedTo: 'user-2',
    assignedToName: 'Bob',
    estimatedHours: 5,
    actualHours: 2,
    sprintId: 2,
    sprintName: 'Sprint 2',
    parentId: 0,
    parentName: '',
  },
  {
    id: 3,
    name: 'Ship feature',
    description: 'Complete board release',
    typeCode: 'task',
    typeDescription: 'Task',
    priorityCode: 'low',
    priorityDescription: 'Low',
    statusCode: 'done',
    statusDescription: 'Done',
    assignedTo: 'user-1',
    assignedToName: 'Alice',
    estimatedHours: 2,
    actualHours: 2,
    sprintId: 1,
    sprintName: 'Sprint 1',
    parentId: 0,
    parentName: '',
  },
];

describe('Board', () => {
  let component: Board;
  let fixture: ComponentFixture<Board>;

  let jobService: {
    getJobStatuses: ReturnType<typeof vi.fn>;
    getJobs: ReturnType<typeof vi.fn>;
    updateJobStatus: ReturnType<typeof vi.fn>;
  };
  
  let sprintService: { getSprints: ReturnType<typeof vi.fn> };
  let router: { navigate: ReturnType<typeof vi.fn> };

  beforeEach(async () => {
    sprintService = {
      getSprints: vi.fn(() => ({ value: () => mockSprints })),
    };

    jobService = {
      getJobStatuses: vi.fn(() => ({ value: () => mockStatuses })),
      getJobs: vi.fn(() => ({ value: () => mockJobs })),
      updateJobStatus: vi.fn(() => of({} as any)),
    };

    router = {
      navigate: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [Board],
      providers: [
        { provide: SprintService, useValue: sprintService },
        { provide: JobService, useValue: jobService },
        { provide: Router, useValue: router },
        provideRouter([]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Board);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should group jobs by status into kanban columns', () => {
    expect(component.columns().map((column) => column.id)).toEqual(['todo', 'inprogress', 'done']);

    expect(component.columns()[0].jobs.map((job) => job.name)).toEqual(['Write tests']);
    expect(component.columns()[1].jobs.map((job) => job.name)).toEqual(['Refine board filter']);
    expect(component.columns()[2].jobs.map((job) => job.name)).toEqual(['Ship feature']);
  });

  it('should show all job columns when no sprint filter is selected', () => {
    expect(component.selectedSprint()).toBe('all');
    expect(component.filteredColumns()).toHaveLength(3);
    
    expect(
      component.filteredColumns().reduce((count, column) => count + column.jobs.length, 0),
    ).toBe(3);
  });

  it('should filter jobs for the selected sprint', () => {
    component.selectedSprint.set(2);
    expect(component.filteredColumns().map((column) => column.jobs.length)).toEqual([0, 1, 0]);
    expect(component.filteredColumns()[1].jobs[0].sprintId).toBe(2);
  });

  it('should update the selected sprint when the dropdown changes', () => {
    const event = { value: 1 } as any;
    component.onSprintChange(event);
    expect(component.selectedSprint()).toBe(1);
  });

  it('should navigate to the job edit page when a job card title is clicked', () => {
    component.onJobClick(mockJobs[0]);
    expect(router.navigate).toHaveBeenCalledWith(['/jobs/edit/1']);
  });

  it('should persist the new status when a job is dropped into another column', () => {
    const oldColumn = component.columns().find((column) => column.id === 'todo')!;
    const newColumn = component.columns().find((column) => column.id === 'done')!;

    const dropEvent = {
      previousContainer: { data: oldColumn.jobs, id: 'todo' },
      container: { data: newColumn.jobs, id: 'done' },
      previousIndex: 0,
      currentIndex: 0,
    } as any;

    component.onJobDrop(dropEvent);

    expect(jobService.updateJobStatus).toHaveBeenCalledWith(1, 'done');
  });
});
