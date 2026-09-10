import { CommonModule, Location } from '@angular/common';
import { Component, computed, effect, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { JobService } from '../../job-service/job-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { Job } from '../../models/job';
import { SprintService } from '../../../sprint/sprint-service/sprint-service';
import { UserService } from '../../../user/user-service/user-service';

@Component({
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    RouterLink,
  ],
  selector: 'app-job-form',
  styleUrl: './job-form.css',
  templateUrl: './job-form.html',
})
export class JobForm {
  private jobService = inject(JobService);
  private sprintService = inject(SprintService);
  private userService = inject(UserService);
  private notificationService = inject(NotificationService);

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private location = inject(Location);
  private fb = inject(FormBuilder);

  isEditMode: boolean = false;
  pageTitle: string = 'Create Job';

  saving = signal(false);
  private jobId = signal<number | null>(null);

  jobs = this.jobService.getJobs();
  parentJobs = computed(() => {
    const jobs: Job[] = this.jobs.value() ?? [];
    return jobs
      .filter((job) => job.parentId === null && job.id !== this.jobId())
      .sort((a, b) => a.name.localeCompare(b.name));
  });

  jobTypes = this.jobService.getJobTypes();
  jobStatuses = this.jobService.getJobStatuses();
  jobPriorities = this.jobService.getJobPriorities();
  users = this.userService.getUsers();
  sprints = this.sprintService.getSprints();

  jobResource = this.jobService.getJobById(this.jobId);

  jobForm = this.fb.group({
    id: new FormControl<number | null>(null),
    sprintId: new FormControl<number | null>(null),
    name: ['', [Validators.required]],
    description: [''],
    typeCode: [''],
    statusCode: [''],
    priorityCode: [''],
    assignedTo: [''],
    estimatedHours: [0.0],
    actualHours: [0.0],
    parentId: new FormControl<number | null>(null),
  });

  constructor() {
    effect(() => {
      if (!this.jobResource.hasValue()) {
        return;
      }

      const job = this.jobResource.value();

      if (job) {
        this.jobForm.patchValue({
          id: job.id,
          sprintId: job.sprintId,
          name: job.name,
          description: job.description,
          typeCode: job.typeCode,
          statusCode: job.statusCode,
          priorityCode: job.priorityCode,
          assignedTo: job.assignedTo,
          estimatedHours: job.estimatedHours,
          actualHours: job.actualHours,
          parentId: job.parentId,
        });
      }
    });
  }

  ngOnInit() {
    const jobId = this.route.snapshot.paramMap.get('id');

    console.log('ngOnInit - jobId:', jobId);

    if (jobId) {
      this.isEditMode = true;
      this.pageTitle = 'Edit Job';
      this.jobId.set(parseInt(jobId));
    }
  }

  onSubmit() {
    console.log('onSubmit - Form:', this.jobForm.value);

    if (this.jobForm.valid) {
      const formValue = this.jobForm.value;

      const job: Job = formValue as Job;

      if (this.isEditMode) {
        this.updateJob(job);
      } else {
        this.createJob(job);
      }
    }
  }

  private createJob(job: Job) {
    this.saving.set(true);

    console.log('createJob - Job:', job);

    this.jobService.createJob(job).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Job created successfully:', response);

        this.notificationService.notifySuccess('Job created successfully.');
        this.goBack();
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error creating job');
      },
    });
  }

  private updateJob(job: Job) {
    this.saving.set(true);

    console.log('updateJob - Job:', job);

    this.jobService.updateJob(job).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Job updated successfully:', response);

        this.notificationService.notifySuccess('Job updated successfully.');
        this.goBack();
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error updating job');
      },
    });
  }

  goBack() {
    this.location.back();
  }
}
