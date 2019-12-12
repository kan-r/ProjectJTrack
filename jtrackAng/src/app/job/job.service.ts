import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { Job, JobSO } from './job';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/job";

    getJobList(): Observable<Job[]>{
      this.clearError();
      return this.httpClient.get<Job[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job  list')),
          catchError(this.handleError<Job[]>('Get Job  list', []))
        );
    }

    getJobList2(jobSO: JobSO): Observable<Job[]>{
      this.clearError();
      const url = `${this.baseUrl}/SO`;
      return this.httpClient.post<Job[]>(url, jobSO, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job  list')),
          catchError(this.handleError<Job[]>('Get Job  list', []))
        );
    }

    getParentJobList(): Observable<Job[]>{
      this.clearError();
      const url: string = ConfigService.baseUrl + "/parentJob";
      return this.httpClient.get<Job[]>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Parent Job  list')),
          catchError(this.handleError<Job[]>('Get Parent Job  list', []))
        );
    }
  
    getJob(jobNo: string): Observable<Job>{
      this.clearError();
      const url = `${this.baseUrl}/${jobNo}`;
  
      return this.httpClient.get<Job>(url, this.authService.getHttpOptions())
        .pipe(
          tap((job: Job) => this.log(`Got Job  ${job.jobNo}`)),
          catchError(this.handleError<Job>('Get Job '))
        );
    }
  
    addJob(job: Job): Observable<Job> {
      this.clearError();
      return this.httpClient.post<Job>(this.baseUrl, job, this.authService.getHttpOptions())
        .pipe(
          tap((newJob: Job) => this.log(`Created Job  ${newJob.jobNo}`)),
          catchError(this.handleError<Job>(`Create Job  ${job.jobNo}`))
        );
    }
  
    updateJob(job: Job): Observable<Job> {
      this.clearError();
      return this.httpClient.put<Job>(this.baseUrl, job, this.authService.getHttpOptions())
        .pipe(
          tap((newJob: Job) => this.log(`Updated Job  ${newJob.jobNo}`)),
          catchError(this.handleError<Job>(`Update Job  ${job.jobNo})`))
        );
    }
  
    deleteJob(jobNo: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobNo}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job  ${jobNo}`)),
          catchError(this.handleError<Object>(`Delete Job  ${jobNo}`))
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
      this.messageService.log(`JobService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`JobService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}
