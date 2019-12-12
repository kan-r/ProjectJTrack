import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { Timesheet, TimesheetSO } from './timesheet';

@Injectable({
  providedIn: 'root'
})
export class TimesheetService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/timesheet";

    getTimesheetList(): Observable<Timesheet[]>{
      this.clearError();
      return this.httpClient.get<Timesheet[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Timesheet  list')),
          catchError(this.handleError<Timesheet[]>('Get Timesheet  list', []))
        );
    }

    getTimesheetList2(timesheetSO: TimesheetSO): Observable<Timesheet[]> {
      this.clearError();
      const url = `${this.baseUrl}/SO`;

      return this.httpClient.post<Timesheet[]>(url, timesheetSO, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Timesheet  list')),
          catchError(this.handleError<Timesheet[]>('Get Timesheet  list', []))
        );
    }
  
    getTimesheet(timesheetId: string): Observable<Timesheet>{
      this.clearError();
      const url = `${this.baseUrl}/${timesheetId}`;
  
      return this.httpClient.get<Timesheet>(url, this.authService.getHttpOptions())
        .pipe(
          tap((timesheet: Timesheet) => this.log(`Got Timesheet  ${timesheet.timesheetId}`)),
          catchError(this.handleError<Timesheet>('Get Timesheet '))
        );
    }
  
    addTimesheet(timesheet: Timesheet): Observable<Timesheet> {
      this.clearError();
      return this.httpClient.post<Timesheet>(this.baseUrl, timesheet, this.authService.getHttpOptions())
        .pipe(
          tap((newTimesheet: Timesheet) => this.log(`Created Timesheet  ${newTimesheet.timesheetId}`)),
          catchError(this.handleError<Timesheet>(`Create Timesheet  ${timesheet.timesheetId}`))
        );
    }
  
    updateTimesheet(timesheet: Timesheet): Observable<Timesheet> {
      this.clearError();
      return this.httpClient.put<Timesheet>(this.baseUrl, timesheet, this.authService.getHttpOptions())
        .pipe(
          tap((newTimesheet: Timesheet) => this.log(`Updated Timesheet  ${newTimesheet.timesheetId}`)),
          catchError(this.handleError<Timesheet>(`Update Timesheet  ${timesheet.timesheetId})`))
        );
    }
  
    deleteTimesheet(timesheetId: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${timesheetId}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Timesheet  ${timesheetId}`)),
          catchError(this.handleError<Object>(`Delete Timesheet  ${timesheetId}`))
        );
    }
  
    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failedS
     * @param result - optional value to return as the observable result
     */
    private handleError<T> (operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        this.logError(`${operation} failed: ${error.error}`);
        return throwError(error);
      };
    }
  
    private log(message: string) {
      this.messageService.log(`TimesheetService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`TimesheetService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}