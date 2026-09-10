import { Routes } from '@angular/router';
import { Sprints } from './features/sprint/pages/sprints/sprints';
import { Board } from './features/board/pages/board/board';
import { Jobs } from './features/job/pages/jobs/jobs';
import { Timesheets } from './features/timesheet/pages/timesheets/timesheets';
import { SprintForm } from './features/sprint/pages/sprint-form/sprint-form';
import { authGuard } from './core/auth/auth-guard/auth-guard';
import { UnauthorizedPage } from './core/auth/unauthorized-page/unauthorized-page';
import { JobForm } from './features/job/pages/job-form/job-form';
import { TimesheetForm } from './features/timesheet/pages/timesheet-form/timesheet-form';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'board',
    pathMatch: 'full',
  },
  {
    path: 'unauthorized',
    component: UnauthorizedPage,
  },
  {
    path: 'board',
    component: Board,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
  {
    path: 'sprints',
    component: Sprints,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
  {
    path: 'sprints/add',
    component: SprintForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager'] },
  },
  {
    path: 'sprints/edit/:id',
    component: SprintForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager'] },
  },
  {
    path: 'jobs',
    component: Jobs,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
  {
    path: 'jobs/add',
    component: JobForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager'] },
  },
  {
    path: 'jobs/edit/:id',
    component: JobForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager'] },
  },
  {
    path: 'timesheets',
    component: Timesheets,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
  {
    path: 'timesheets/add',
    component: TimesheetForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
  {
    path: 'timesheets/edit/:id',
    component: TimesheetForm,
    canActivate: [authGuard],
    data: { roles: ['admin', 'manager', 'user'] },
  },
];
