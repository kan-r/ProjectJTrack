import { inject, Service, Signal } from '@angular/core';
import { environment as env } from '../../../../environments/environment';
import { HttpClient, httpResource } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobPriority } from '../models/job-priority';
import { JobStatus } from '../models/job-status';
import { JobType } from '../models/job-type';
import { Job } from '../models/job';

@Service()
export class JobService {
  private httpClient = inject(HttpClient);

  getJobPriorities() {
    return httpResource<JobPriority[]>(() => `${env.apiUrl}/jobPriorities`);
  }

  getJobStatuses() {
    return httpResource<JobStatus[]>(() => `${env.apiUrl}/jobStatuses`);
  }

  getJobTypes() {
    return httpResource<JobType[]>(() => `${env.apiUrl}/jobTypes`);
  }

  getJobs() {
    return httpResource<Job[]>(() => `${env.apiUrl}/jobs`);
  }

  getJobById(id: Signal<number | null>) {
    return httpResource<Job>(() => {
      const jobId = id();
      return jobId === null ? undefined : `${env.apiUrl}/jobs/${jobId}`;
    });
  }

  createJob(job: Job): Observable<Job> {
    return this.httpClient.post<Job>(`${env.apiUrl}/jobs`, job);
  }

  updateJob(job: Job): Observable<Job> {
    return this.httpClient.put<Job>(`${env.apiUrl}/jobs/${job.id}`, job);
  }

  updateJobStatus(id: number, statusCode: string): Observable<Job> {
    return this.httpClient.patch<Job>(`${env.apiUrl}/jobs/${id}/status`, { "statusCode": statusCode });
  }

  deleteJob(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${env.apiUrl}/jobs/${id}`);
  }
}
