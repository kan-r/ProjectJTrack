import { Component, signal, computed, inject, Signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  CdkDragDrop,
  CdkDrag,
  CdkDropList,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule, MatSelectChange } from '@angular/material/select';
import { SprintService } from '../../../sprint/sprint-service/sprint-service';
import { JobService } from '../../../job/job-service/job-service';
import { Job } from '../../../job/models/job';
import { Router } from "@angular/router";

export interface KanbanColumn {
  id: string;
  name: string;
  jobs: Job[];
}

@Component({
  imports: [
    CommonModule,
    CdkDropList,
    CdkDrag,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
],
  selector: 'app-board',
  styleUrl: './board.css',
  templateUrl: './board.html',
})
export class Board {
  private sprintService = inject(SprintService);
  private jobService = inject(JobService);

  private router = inject(Router); 

  sprints = this.sprintService.getSprints();
  selectedSprint = signal<number | 'all'>('all');

  private jobStatuses = this.jobService.getJobStatuses();
  private jobs = this.jobService.getJobs();

  private jobStatusToJobsMap = computed<Map<string, Job[]>>(() => {
    const map = new Map<string, Job[]>();
    const jobs = this.jobs.value() ?? [];

    jobs.forEach((job) => {
      if (!map.has(job.statusCode)) {
        map.set(job.statusCode, []);
      }
      map.get(job.statusCode)!.push(job);
    });

    return map;
  });

  columns: Signal<KanbanColumn[]> = computed(() => {
    const jobStatuses = this.jobStatuses.value() ?? [];
    const map = this.jobStatusToJobsMap();

    return jobStatuses.map((status) => ({
      id: status.code,
      name: status.description,
      jobs: map.get(status.code) ?? [],
    }));
  });

  // Derived signal: Automatically filters jobs based on the active sprint
  filteredColumns = computed(() => {
    const currentSprint = this.selectedSprint();
    const originalColumns = this.columns();

    if (currentSprint === 'all') {
      return originalColumns;
    }

    console.log('Filtering columns for sprint:', currentSprint);

    const columns = structuredClone(originalColumns);

    columns.forEach((column) => {
      column.jobs = column.jobs.filter((job) => job.sprintId && job.sprintId === currentSprint);
    });

    return columns;
  });

  // Track all unique column IDs for cross-list drag configurations
  get connectedTo(): string[] {
    return this.columns().map((col) => col.id);
  }

  // Updates the sprint filter value based on mat-select choice
  onSprintChange(event: MatSelectChange) {
    console.log('Sprint changed:', event.value);
    this.selectedSprint.set(event.value);
  }

  onJobClick(job: Job) {
    console.log('Job clicked:', job);
    this.router.navigate([`/jobs/edit/${job.id}`]);
  }

  // Handle the drag-and-drop item transitions
  onJobDrop(event: CdkDragDrop<Job[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );

      const movedJob: Job = event.container.data[event.currentIndex];

      this.updateJobStatus(movedJob.id, event.container.id);
    }
  }

  private updateJobStatus(jobId: number, statusCode: string) {
    console.log('updateJobStatus - Job Status:', statusCode);

    this.jobService.updateJobStatus(jobId, statusCode).subscribe({
      next: (response) => {
        console.log('Job status updated successfully:', response);
      }
    });
  }
}
