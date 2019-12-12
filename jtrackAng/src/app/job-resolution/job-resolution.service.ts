import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { JobResolution } from './job-resolution';

@Injectable({
  providedIn: 'root'
})
export class JobResolutionService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/jobResolution";

    getJobResolutionList(): Observable<JobResolution[]>{
      this.clearError();
      return this.httpClient.get<JobResolution[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job Resolution list')),
          catchError(this.handleError<JobResolution[]>('Get Job Resolution list', []))
        );
    }
  
    getJobResolution(jobResolution: string): Observable<JobResolution>{
      this.clearError();
      const url = `${this.baseUrl}/${jobResolution}`;
  
      return this.httpClient.get<JobResolution>(url, this.authService.getHttpOptions())
        .pipe(
          tap((jobresolution: JobResolution) => this.log(`Got Job Resolution ${jobresolution.jobResolution}`)),
          catchError(this.handleError<JobResolution>('Get Job Resolution'))
        );
    }
  
    addJobResolution(jobResolution: JobResolution): Observable<JobResolution> {
      this.clearError();
      return this.httpClient.post<JobResolution>(this.baseUrl, jobResolution, this.authService.getHttpOptions())
        .pipe(
          tap((newJobResolution: JobResolution) => this.log(`Created Job Resolution ${newJobResolution.jobResolution}`)),
          catchError(this.handleError<JobResolution>(`Create Job Resolution ${jobResolution.jobResolution}`))
        );
    }
  
    updateJobResolution(jobResolution: JobResolution): Observable<JobResolution> {
      this.clearError();
      return this.httpClient.put<JobResolution>(this.baseUrl, jobResolution, this.authService.getHttpOptions())
        .pipe(
          tap((newJobResolution: JobResolution) => this.log(`Updated Job Resolution ${newJobResolution.jobResolution}`)),
          catchError(this.handleError<JobResolution>(`Update Job Resolution ${jobResolution.jobResolution})`))
        );
    }
  
    deleteJobResolution(jobResolution: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobResolution}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job Resolution ${jobResolution}`)),
          catchError(this.handleError<Object>(`Delete Job Resolution ${jobResolution}`))
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
      this.messageService.log(`JobResolutionService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`JobResolutionService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}