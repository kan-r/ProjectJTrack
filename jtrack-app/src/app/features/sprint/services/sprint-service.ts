import { inject, Service, type Signal } from '@angular/core';
import { Sprint } from '../models/sprint';
import { HttpClient, httpResource } from '@angular/common/http';
import { environment as env } from '../../../../../src/environments/environment';
import { Observable } from 'rxjs';
import { SprintStatus } from '../models/sprint-status';

@Service()
export class SprintService {
  private httpClient = inject(HttpClient);

  getSprintStatuses() {
    return httpResource<SprintStatus[]>(() => `${env.apiUrl}/sprintStatuses`);
  }

  getSprints() {
    return httpResource<Sprint[]>(() => `${env.apiUrl}/sprints`);
  }

  getSprintById(id: Signal<number | null>) {
    return httpResource<Sprint>(() => {
      const sprintId = id();
      return sprintId === null ? undefined : `${env.apiUrl}/sprints/${sprintId}`;
    });
  }

  createSprint(sprint: Sprint): Observable<Sprint> {
    return this.httpClient.post<Sprint>(`${env.apiUrl}/sprints`, sprint);
  }

  updateSprint(sprint: Sprint): Observable<Sprint> {
    return this.httpClient.put<Sprint>(`${env.apiUrl}/sprints/${sprint.id}`, sprint);
  }

  deleteSprint(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${env.apiUrl}/sprints/${id}`);
  }
}
