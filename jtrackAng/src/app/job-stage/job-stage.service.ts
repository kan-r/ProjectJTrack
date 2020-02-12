import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ConfigService } from '../config/config.service';
import { MessageService } from '../message/message.service';
import { AuthService } from '../auth/auth.service';
import { JobStage } from './job-stage';

@Injectable({
  providedIn: 'root'
})
export class JobStageService {

  constructor(
    private httpClient: HttpClient, 
    private authService: AuthService, 
    private messageService: MessageService) { }

    private baseUrl: string = ConfigService.baseUrl + "/jobStage";

    getJobStageList(): Observable<JobStage[]>{
      this.clearError();
      return this.httpClient.get<JobStage[]>(this.baseUrl, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log('Got Job Stage list')),
          catchError(this.handleError<JobStage[]>('Get Job Stage list', []))
        );
    }
  
    getJobStage(jobStage: string): Observable<JobStage>{
      this.clearError();
      const url = `${this.baseUrl}/${jobStage}`;
  
      return this.httpClient.get<JobStage>(url, this.authService.getHttpOptions())
        .pipe(
          tap((jobstage: JobStage) => this.log(`Got Job Stage ${jobstage.jobStage}`)),
          catchError(this.handleError<JobStage>('Get Job Stage'))
        );
    }
  
    addJobStage(jobStage: JobStage): Observable<JobStage> {
      let j  = jobStage.jobStage;
      if(j == null || j.trim() === ''){
          this.logError('Job Stage is required');
          return throwError('Job Stage is required');
      }

      this.clearError();
      return this.httpClient.post<JobStage>(this.baseUrl, jobStage, this.authService.getHttpOptions())
        .pipe(
          tap((newJobStage: JobStage) => this.log(`Created Job Stage ${newJobStage.jobStage}`)),
          catchError(this.handleError<JobStage>(`Create Job Stage ${jobStage.jobStage}`))
        );
    }
  
    updateJobStage(jobStage: JobStage): Observable<JobStage> {
      this.clearError();
      return this.httpClient.put<JobStage>(this.baseUrl, jobStage, this.authService.getHttpOptions())
        .pipe(
          tap((newJobStage: JobStage) => this.log(`Updated Job Stage ${newJobStage.jobStage}`)),
          catchError(this.handleError<JobStage>(`Update Job Stage ${jobStage.jobStage})`))
        );
    }
  
    deleteJobStage(jobStage: string): Observable<Object> {
      this.clearError();
      const url = `${this.baseUrl}/${jobStage}`;
  
      return this.httpClient.delete<Object>(url, this.authService.getHttpOptions())
        .pipe(
          tap(_ => this.log(`Deleted Job Stage ${jobStage}`)),
          catchError(this.handleError<Object>(`Delete Job Stage ${jobStage}`))
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
      this.messageService.log(`JobStageService: ${message}`);
    }
  
    private logError(error: string) {
      this.messageService.logError(`JobStageService: ${error}`);
    }

    private clearError(){
      this.messageService.clearError();
    }
}
