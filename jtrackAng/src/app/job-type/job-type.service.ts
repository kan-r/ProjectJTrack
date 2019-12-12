import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { JobType } from './job-type';

@Injectable({
  providedIn: 'root'
})
export class JobTypeService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/jobType";

    getJobTypeList(): Observable<JobType[]>{
      this.clearError();
      return this.httpClient.get<JobType[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job Type list')),
          catchError(this.handleError<JobType[]>('Get Job Type list', []))
        );
    }
  
    getJobType(jobType: string): Observable<JobType>{
      this.clearError();
      const url = `${this.baseUrl}/${jobType}`;
  
      return this.httpClient.get<JobType>(url, this.authService.getHttpOptions())
        .pipe(
          tap((jobtype: JobType) => this.log(`Got Job Type ${jobtype.jobType}`)),
          catchError(this.handleError<JobType>('Get Job Type'))
        );
    }
  
    addJobType(jobType: JobType): Observable<JobType> {
      this.clearError();
      return this.httpClient.post<JobType>(this.baseUrl, jobType, this.authService.getHttpOptions())
        .pipe(
          tap((newJobType: JobType) => this.log(`Created Job Type ${newJobType.jobType}`)),
          catchError(this.handleError<JobType>(`Create Job Type ${jobType.jobType}`))
        );
    }
  
    updateJobType(jobType: JobType): Observable<JobType> {
      this.clearError();
      return this.httpClient.put<JobType>(this.baseUrl, jobType, this.authService.getHttpOptions())
        .pipe(
          tap((newJobType: JobType) => this.log(`Updated Job Type ${newJobType.jobType}`)),
          catchError(this.handleError<JobType>(`Update Job Type ${jobType.jobType})`))
        );
    }
  
    deleteJobType(jobType: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobType}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job Type ${jobType}`)),
          catchError(this.handleError<Object>(`Delete Job Type ${jobType}`))
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
