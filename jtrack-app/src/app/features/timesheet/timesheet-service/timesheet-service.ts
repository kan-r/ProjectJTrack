import { inject, Service, Signal } from '@angular/core';
import { environment as env } from '../../../../environments/environment';
import { HttpClient, httpResource } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Timesheet } from '../models/timesheet';

@Service()
export class TimesheetService {
  private httpClient = inject(HttpClient);
  
  getTimesheets() {
    return httpResource<Timesheet[]>(() => `${env.apiUrl}/timesheets`);
  }

  getTimesheetById(id: Signal<number | null>) {
    return httpResource<Timesheet>(() => {
      const timesheetId = id();
      return timesheetId === null ? undefined : `${env.apiUrl}/timesheets/${timesheetId}`;
    });
  }

  createTimesheet(timesheet: Timesheet): Observable<Timesheet> {
    return this.httpClient.post<Timesheet>(`${env.apiUrl}/timesheets`, timesheet);
  }

  updateTimesheet(timesheet: Timesheet): Observable<Timesheet> {
    return this.httpClient.put<Timesheet>(`${env.apiUrl}/timesheets/${timesheet.id}`, timesheet);
  }

  deleteTimesheet(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${env.apiUrl}/timesheets/${id}`);
  } 
}
