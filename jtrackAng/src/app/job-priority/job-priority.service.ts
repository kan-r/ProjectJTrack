import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { JobPriority } from './job-priority';

@Injectable({
  providedIn: 'root'
})
export class JobPriorityService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/jobPriority";

    getJobPriorityList(): Observable<JobPriority[]>{
      this.clearError();
      return this.httpClient.get<JobPriority[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job Priority list')),
          catchError(this.handleError<JobPriority[]>('Get Job Priority list', []))
        );
    }
  
    getJobPriority(jobPriority: string): Observable<JobPriority>{
      this.clearError();
      const url = `${this.baseUrl}/${jobPriority}`;
  
      return this.httpClient.get<JobPriority>(url, this.authService.getHttpOptions())
        .pipe(
          tap((jobtype: JobPriority) => this.log(`Got Job Priority ${jobtype.jobPriority}`)),
          catchError(this.handleError<JobPriority>('Get Job Priority'))
        );
    }
  
    addJobPriority(jobPriority: JobPriority): Observable<JobPriority> {
      this.clearError();
      return this.httpClient.post<JobPriority>(this.baseUrl, jobPriority, this.authService.getHttpOptions())
        .pipe(
          tap((newJobPriority: JobPriority) => this.log(`Created Job Priority ${newJobPriority.jobPriority}`)),
          catchError(this.handleError<JobPriority>(`Create Job Priority ${jobPriority.jobPriority}`))
        );
    }
  
    updateJobPriority(jobPriority: JobPriority): Observable<JobPriority> {
      this.clearError();
      return this.httpClient.put<JobPriority>(this.baseUrl, jobPriority, this.authService.getHttpOptions())
        .pipe(
          tap((newJobPriority: JobPriority) => this.log(`Updated Job Priority ${newJobPriority.jobPriority}`)),
          catchError(this.handleError<JobPriority>(`Update Job Priority ${jobPriority.jobPriority})`))
        );
    }
  
    deleteJobPriority(jobPriority: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobPriority}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job Priority ${jobPriority}`)),
          catchError(this.handleError<Object>(`Delete Job Priority ${jobPriority}`))
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
      this.messageService.log(`JobPriorityService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`JobPriorityService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}
