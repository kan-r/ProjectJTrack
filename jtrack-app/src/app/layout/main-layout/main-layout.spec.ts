import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { MainLayout } from './main-layout';

describe('MainLayout', () => {
  let component: MainLayout;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainLayout],
      providers: [provideRouter([])],
    }).compileComponents();

    const fixture = TestBed.createComponent(MainLayout);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should define the expected sidebar navigation items', () => {
    expect(component['sidebarItems']).toEqual([
      { label: 'Board', route: '/board', icon: 'view_kanban' },
      { label: 'Sprints', route: '/sprints', icon: 'timeline' },
      { label: 'Jobs', route: '/jobs', icon: 'assignment' },
      { label: 'Timesheets', route: '/timesheets', icon: 'history' },
    ]);
  });

  it('should expose the board, sprints, jobs, and timesheets routes', () => {
    const routes = component['sidebarItems'].map((item) => item.route);

    expect(routes).toContain('/board');
    expect(routes).toContain('/sprints');
    expect(routes).toContain('/jobs');
    expect(routes).toContain('/timesheets');
  });

  it('should give each sidebar item an icon and label', () => {
    const allHaveLabel = component['sidebarItems'].every((item) => !!item.label);
    const allHaveIcon = component['sidebarItems'].every((item) => !!item.icon);
    const allHaveRoute = component['sidebarItems'].every((item) => !!item.route);

    expect(allHaveLabel).toBe(true);
    expect(allHaveIcon).toBe(true);
    expect(allHaveRoute).toBe(true);
  });
});
