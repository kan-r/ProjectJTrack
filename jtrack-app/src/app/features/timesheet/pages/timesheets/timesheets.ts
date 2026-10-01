import { Component, computed, inject, signal } from '@angular/core';
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
import { UserService } from '../../../user/user-service/user-service';
import { MatSelectChange, MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  imports: [
    MatTableModule, 
    MatProgressSpinnerModule, 
    MatButtonModule, 
    MatIconModule, 
    RouterLink,
    MatFormFieldModule,
    MatSelectModule,
  ],
  selector: 'app-timesheets',
  styleUrl: './timesheets.css',
  templateUrl: './timesheets.html',
})
export class Timesheets {
  private timesheetService = inject(TimesheetService);
  private userService = inject(UserService);
  private confirmationService = inject(ConfirmationService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  private router = inject(Router);

  canCreate = this.authService.hasAnyRole(['admin', 'manager', 'user']);
  canEdit = this.canCreate;

  deleting = signal(false);

  users = this.userService.getUsers();
  selectedUser = signal<String | 'all'>('all');

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

  filteredTimesheets = computed(() => {
    const selectedUser = this.selectedUser();
    if (selectedUser === 'all') {
      return this.timesheetResource.value();
    }
    return this.timesheetResource.value()?.filter((timesheet) => timesheet.userId === selectedUser);
  });

  ngOnInit() {
    console.log('Timesheets component initialized.');
    const currentUser = this.authService.currentUser();
    if (currentUser) {
      this.selectedUser.set(currentUser.id);
    }
  }
  
  onUserChange(event: MatSelectChange) {
    console.log('User changed:', event.value);
    this.selectedUser.set(event.value);
  }

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
