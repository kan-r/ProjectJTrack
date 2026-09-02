import { Component, inject, signal } from '@angular/core';
import { SprintService } from '../../services/sprint-service';
import { Sprint } from '../../models/sprint';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink } from '@angular/router';
import { formatIsoToLocaleDate } from '../../../../core/utils/date-utils';
import { NotificationService } from '../../../../core/services/notification-service';
import { ConfirmationService } from '../../../../core/services/confirmation-service';
import { AuthService } from '../../../../core/services/auth-service';

@Component({
  imports: [MatTableModule, MatProgressSpinnerModule, MatButtonModule, MatIconModule, RouterLink],
  selector: 'app-sprints',
  styleUrl: './sprints.css',
  templateUrl: './sprints.html',
})
export class Sprints {
  private sprintService = inject(SprintService);
  private confirmationService = inject(ConfirmationService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  private router = inject(Router);

  canCreate = this.authService.hasAnyRole(['admin', 'manager']);
  canEdit = this.canCreate;

  deleting = signal(false);

  sprintResource = this.sprintService.getSprints();

  columns = [
    { name: 'id', header: 'ID', cell: (sprint: Sprint) => `${sprint.id}` },
    { name: 'name', header: 'Name', cell: (sprint: Sprint) => sprint.name },
    {
      name: 'statusDescription',
      header: 'Status',
      cell: (sprint: Sprint) => sprint.statusDescription,
    },
    {
      name: 'startDate',
      header: 'Start Date',
      cell: (sprint: Sprint) => formatIsoToLocaleDate(sprint.startDate),
    },
    {
      name: 'endDate',
      header: 'End Date',
      cell: (sprint: Sprint) => formatIsoToLocaleDate(sprint.endDate),
    },
    { name: 'edit', header: '', cell: () => 'edit' },
    { name: 'delete', header: '', cell: () => 'delete' },
  ];

  displayedColumns = this.columns.map((column) => column.name);

  onActionClick(action: string, id: number) {
    console.log(`onActionClick: ${action}, ID: ${id}`);

    if (action === 'edit') {
      this.router.navigate(['/sprints/edit', id]);
    } else if (action === 'delete') {
      this.confirmAndDeleteSprint(id);
    }
  }

  private confirmAndDeleteSprint(id: number) {
    this.confirmationService
      .confirm('Delete Sprint for ID: ' + id, 'Are you sure you want to delete?')
      .subscribe((confirmed) => {
        if (confirmed) {
          this.deleteSprint(id);
        }
      });
  }

  private deleteSprint(id: number) {
    this.deleting.set(true);

    console.log(`deleteSprint - ID: ${id}`);

    this.sprintService.deleteSprint(id).subscribe({
      next: () => {
        this.deleting.set(false);
        console.log(`Sprint deleted successfully for ID: ${id}`);

        this.notificationService.notifySuccess('Sprint deleted successfully.');
        this.sprintResource.reload();
      },
      error: (error: unknown) => {
        this.deleting.set(false);
        console.error(`Error deleting sprint for ID: ${id}`);
      },
    });
  }
}
