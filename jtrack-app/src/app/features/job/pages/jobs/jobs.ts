import { Component, inject, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink } from '@angular/router';
import { JobService } from '../../job-service/job-service';
import { ConfirmationService } from '../../../../shared/confirmation/confirmation-service/confirmation-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';
import { Job } from '../../models/job';

@Component({
  imports: [MatTableModule, MatProgressSpinnerModule, MatButtonModule, MatIconModule, RouterLink],
  selector: 'app-jobs',
  styleUrl: './jobs.css',
  templateUrl: './jobs.html',
})
export class Jobs {
  private jobService = inject(JobService);
  private confirmationService = inject(ConfirmationService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  private router = inject(Router);

  canCreate = this.authService.hasAnyRole(['admin', 'manager']);
  canEdit = this.canCreate;

  deleting = signal(false);

  jobResource = this.jobService.getJobs();

  columns = [
    { name: 'id', header: 'ID', cell: (job: Job) => `${job.id}`, visible: true },
    { name: 'name', header: 'Name', cell: (job: Job) => `${job.name}`, visible: true },
    {
      name: 'description',
      header: 'Description',
      cell: (job: Job) => `${job.description ?? ''}`,
      visible: true,
    },
    {
      name: 'typeDescription',
      header: 'Type',
      cell: (job: Job) => `${job.typeDescription}`,
      visible: true,
    },
    {
      name: 'statusDescription',
      header: 'Status',
      cell: (job: Job) => `${job.statusDescription}`,
      visible: true,
    },
    {
      name: 'priorityDescription',
      header: 'Priority',
      cell: (job: Job) => `${job.priorityDescription}`,
      visible: true,
    },
    {
      name: 'assignedTo',
      header: 'Assigned To ID',
      cell: (job: Job) => `${job.assignedTo}`,
      visible: false,
    },
    {
      name: 'assignedToName',
      header: 'Assigned To',
      cell: (job: Job) => `${job.assignedToName ?? ''}`,
      visible: true,
    },
    {
      name: 'estimatedHours',
      header: 'Estimated Hours',
      cell: (job: Job) => `${job.estimatedHours}`,
      visible: true,
    },
    {
      name: 'actualHours',
      header: 'Actual Hours',
      cell: (job: Job) => `${job.actualHours}`,
      visible: true,
    },
    { name: 'parentId', header: 'Parent ID', cell: (job: Job) => `${job.parentId}`, visible: false },
    {
      name: 'parentName',
      header: 'Parent Name',
      cell: (job: Job) => `${job.parentName ?? ''}`,
      visible: true,
    },
    { name: 'sprintId', header: 'Sprint ID', cell: (job: Job) => `${job.sprintId}`, visible: false },
    {
      name: 'sprintName',
      header: 'Sprint Name',
      cell: (job: Job) => `${job.sprintName ?? ''}`,
      visible: true,
    },
    { name: 'edit', header: '', cell: () => 'edit', visible: true },
    { name: 'delete', header: '', cell: () => 'delete', visible: true },
  ];

  displayedColumns = this.columns.filter((column) => column.visible).map((column) => column.name);

  onActionClick(action: string, id: number) {
    console.log(`onActionClick: ${action}, ID: ${id}`);

    if (action === 'edit') {
      this.router.navigate(['/jobs/edit', id]);
    } else if (action === 'delete') {
      this.confirmAndDeleteJob(id);
    }
  }

  private confirmAndDeleteJob(id: number) {
    this.confirmationService
      .confirm('Delete Job for ID: ' + id, 'Are you sure you want to delete?')
      .subscribe((confirmed) => {
        if (confirmed) {
          this.deleteJob(id);
        }
      });
  }

  private deleteJob(id: number) {
    this.deleting.set(true);

    console.log(`deleteJob - ID: ${id}`);

    this.jobService.deleteJob(id).subscribe({
      next: () => {
        this.deleting.set(false);
        console.log(`Job deleted successfully for ID: ${id}`);

        this.notificationService.notifySuccess('Sprint deleted successfully.');
        this.jobResource.reload();
      },
      error: (error: unknown) => {
        this.deleting.set(false);
        console.error(`Error deleting job for ID: ${id}`);
      },
    });
  }
}
