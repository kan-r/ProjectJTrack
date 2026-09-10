import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { Router, RouterLink } from '@angular/router';
import { TimesheetService } from '../../timesheet-service/timesheet-service';
import { ConfirmationService } from '../../../../shared/confirmation/confirmation-service/confirmation-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';
import { Timesheet } from '../../models/timesheet';

@Component({
  imports: [MatTableModule, MatProgressSpinnerModule, MatButtonModule, MatIconModule, RouterLink],
  selector: 'app-timesheets',
  styleUrl: './timesheets.css',
  templateUrl: './timesheets.html',
})
export class Timesheets {
  private timesheetService = inject(TimesheetService);
  private confirmationService = inject(ConfirmationService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  private router = inject(Router);

  canCreate = this.authService.hasAnyRole(['admin', 'manager', 'user']);
  canEdit = this.canCreate;

  deleting = signal(false);

  timesheetResource = this.timesheetService.getTimesheets();

  columns = [
    { name: 'id', header: 'ID', cell: (timesheet: Timesheet) => `${timesheet.id}`, visible: true },
    {
      name: 'userId',
      header: 'User ID',
      cell: (timesheet: Timesheet) => `${timesheet.userId}`,
      visible: false,
    },
    {
      name: 'userName',
      header: 'User',
      cell: (timesheet: Timesheet) => `${timesheet.userName}`,
      visible: true,
    },
    {
      name: 'jobId',
      header: 'Job ID',
      cell: (timesheet: Timesheet) => `${timesheet.jobId}`,
      visible: true,
    },
    {
      name: 'jobName',
      header: 'Job Name',
      cell: (timesheet: Timesheet) => `${timesheet.jobName}`,
      visible: true,
    },
    {
      name: 'workedDate',
      header: 'Worked Date',
      cell: (timesheet: Timesheet) => `${timesheet.workedDate}`,
      visible: true,
    },
    {
      name: 'workedHours',
      header: 'Worked Hours',
      cell: (timesheet: Timesheet) => `${timesheet.workedHours}`,
      visible: true,
    },
    { name: 'edit', header: '', cell: () => 'edit', visible: true },
    { name: 'delete', header: '', cell: () => 'delete', visible: true },
  ];

  displayedColumns = this.columns.filter((column) => column.visible).map((column) => column.name);

  onActionClick(action: string, id: number) {
    console.log(`onActionClick: ${action}, ID: ${id}`);

    if (action === 'edit') {
      this.router.navigate(['/timesheets/edit', id]);
    } else if (action === 'delete') {
      this.confirmAndDeleteTimesheet(id);
    }
  }

  private confirmAndDeleteTimesheet(id: number) {
    this.confirmationService
      .confirm('Delete Timesheet for ID: ' + id, 'Are you sure you want to delete?')
      .subscribe((confirmed) => {
        if (confirmed) {
          this.deleteTimesheet(id);
        }
      });
  }

  private deleteTimesheet(id: number) {
    this.deleting.set(true);

    console.log(`deleteTimesheet - ID: ${id}`);

    this.timesheetService.deleteTimesheet(id).subscribe({
      next: () => {
        this.deleting.set(false);
        console.log(`Timesheet deleted successfully for ID: ${id}`);

        this.notificationService.notifySuccess('Timesheet deleted successfully.');
        this.timesheetResource.reload();
      },
      error: (error: unknown) => {
        this.deleting.set(false);
        console.error(`Error deleting timesheet for ID: ${id}`);
      },
    });
  }
}
