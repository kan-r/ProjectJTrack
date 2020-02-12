import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { TimesheetCode } from './timesheet-code';

@Injectable({
  providedIn: 'root'
})
export class TimesheetCodeService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/timesheetCode";

    getTimesheetCodeList(): Observable<TimesheetCode[]>{
      this.clearError();
      return this.httpClient.get<TimesheetCode[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Timesheet Code list')),
          catchError(this.handleError<TimesheetCode[]>('Get Timesheet Code list', []))
        );
    }
  
    getTimesheetCode(timesheetCode: string): Observable<TimesheetCode>{
      this.clearError();
      const url = `${this.baseUrl}/${timesheetCode}`;
  
      return this.httpClient.get<TimesheetCode>(url, this.authService.getHttpOptions())
        .pipe(
          tap((timesheetcode: TimesheetCode) => this.log(`Got Timesheet Code ${timesheetcode.timesheetCode}`)),
          catchError(this.handleError<TimesheetCode>('Get Timesheet Code'))
        );
    }
  
    addTimesheetCode(timesheetCode: TimesheetCode): Observable<TimesheetCode> {
      let t = timesheetCode.timesheetCode;
      if(t == null || t.trim() === ''){
        this.logError('Timesheet Code is required');
        return throwError('Timesheet Code is required');
      }

      this.clearError();
      return this.httpClient.post<TimesheetCode>(this.baseUrl, timesheetCode, this.authService.getHttpOptions())
        .pipe(
          tap((newTimesheetCode: TimesheetCode) => this.log(`Created Timesheet Code ${newTimesheetCode.timesheetCode}`)),
          catchError(this.handleError<TimesheetCode>(`Create Timesheet Code ${timesheetCode.timesheetCode}`))
        );
    }
  
    updateTimesheetCode(timesheetCode: TimesheetCode): Observable<TimesheetCode> {
      this.clearError();
      return this.httpClient.put<TimesheetCode>(this.baseUrl, timesheetCode, this.authService.getHttpOptions())
        .pipe(
          tap((newTimesheetCode: TimesheetCode) => this.log(`Updated Timesheet Code ${newTimesheetCode.timesheetCode}`)),
          catchError(this.handleError<TimesheetCode>(`Update Timesheet Code ${timesheetCode.timesheetCode})`))
        );
    }
  
    deleteTimesheetCode(timesheetCode: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${timesheetCode}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Timesheet Code ${timesheetCode}`)),
          catchError(this.handleError<Object>(`Delete Timesheet Code ${timesheetCode}`))
        );
    }
  
    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failedS
     * @param result - optional value to return as the observable result
     */
    private handleError<T> (operation = 'operation', result?: T) {
      return this.messageService.handleError(operation, result);
    }
  
    private log(message: string) {
      this.messageService.log(`TimesheetCodeService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`TimesheetCodeService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}
