import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { JobStatus } from './job-status';

@Injectable({
  providedIn: 'root'
})
export class JobStatusService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/jobStatus";

    getJobStatusList(): Observable<JobStatus[]>{
      this.clearError();
      return this.httpClient.get<JobStatus[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job Status list')),
          catchError(this.handleError<JobStatus[]>('Get Job Status list', []))
        );
    }
  
    getJobStatus(jobStatus: string): Observable<JobStatus>{
      this.clearError();
      const url = `${this.baseUrl}/${jobStatus}`;
  
      return this.httpClient.get<JobStatus>(url, this.authService.getHttpOptions())
        .pipe(
          tap((jobStatus: JobStatus) => this.log(`Got Job Status ${jobStatus.jobStatus}`)),
          catchError(this.handleError<JobStatus>('Get Job Status'))
        );
    }
  
    addJobStatus(jobStatus: JobStatus): Observable<JobStatus> {
      this.clearError();
      return this.httpClient.post<JobStatus>(this.baseUrl, jobStatus, this.authService.getHttpOptions())
        .pipe(
          tap((newJobStatus: JobStatus) => this.log(`Created Job Status ${newJobStatus.jobStatus}`)),
          catchError(this.handleError<JobStatus>(`Create Job Status ${jobStatus.jobStatus}`))
        );
    }
  
    updateJobStatus(jobStatus: JobStatus): Observable<JobStatus> {
      this.clearError();
      return this.httpClient.put<JobStatus>(this.baseUrl, jobStatus, this.authService.getHttpOptions())
        .pipe(
          tap((newJobStatus: JobStatus) => this.log(`Updated Job Status ${newJobStatus.jobStatus}`)),
          catchError(this.handleError<JobStatus>(`Update Job Status ${jobStatus.jobStatus})`))
        );
    }
  
    deleteJobStatus(jobStatus: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobStatus}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job Status ${jobStatus}`)),
          catchError(this.handleError<Object>(`Delete Job Status ${jobStatus}`))
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
      this.messageService.log(`JobTypeService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`JobTypeService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}