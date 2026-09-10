import { Component, effect, inject, signal } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { formatLocaleToIsoDate } from '../../../../shared/utils/date-utils';
import { TimesheetService } from '../../timesheet-service/timesheet-service';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';
import { UserService } from '../../../user/user-service/user-service';
import { JobService } from '../../../job/job-service/job-service';
import { Timesheet } from '../../models/timesheet';
import { AuthService } from '../../../../core/auth/auth-service/auth-service';

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
    RouterLink,
    ReactiveFormsModule,
  ],
  selector: 'app-timesheet-form',
  styleUrl: './timesheet-form.css',
  templateUrl: './timesheet-form.html',
})
export class TimesheetForm {
  private timesheetService = inject(TimesheetService);
  private userService = inject(UserService);
  private jobService = inject(JobService);
  private authService = inject(AuthService);
  private notificationService = inject(NotificationService);

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  isEditMode: boolean = false;
  pageTitle: string = 'Create Timesheet';

  saving = signal(false);
  private timesheetId = signal<number | null>(null);

  users = this.userService.getUsers();
  jobs = this.jobService.getJobs();

  currentUser = this.authService.currentUser();

  timesheetResource = this.timesheetService.getTimesheetById(this.timesheetId);

  timesheetForm: FormGroup = this.fb.group({
    id: new FormControl<number | null>(null),
    userId: ['', [Validators.required]],
    jobId: new FormControl<number | null>(null),
    workedDate: [''],
    workedHours: [0.0],
  });

  constructor() {
    effect(() => {
      if (!this.timesheetResource.hasValue()) {
        return;
      }

      const timesheet = this.timesheetResource.value();

      if (timesheet) {
        this.timesheetForm.patchValue({
          id: timesheet.id,
          userId: timesheet.userId,
          jobId: timesheet.jobId,
          workedDate: timesheet.workedDate ? new Date(timesheet.workedDate) : null,
          workedHours: timesheet.workedHours,
        });
      }
    });
  }

  ngOnInit() {
    const timesheetId = this.route.snapshot.paramMap.get('id');

    console.log('ngOnInit - timesheetId:', timesheetId);

    if (timesheetId) {
      this.isEditMode = true;
      this.pageTitle = 'Edit Sprint';
      this.timesheetId.set(parseInt(timesheetId));
    } else {
      this.timesheetForm.patchValue({
        userId: this.currentUser?.id ?? null,
      });
    }
  }

  onSubmit() {
    console.log('onSubmit - Form:', this.timesheetForm.value);

    if (this.timesheetForm.valid) {
      const formValue = this.timesheetForm.value;

      const timesheet: Timesheet = {
        id: formValue.id!,
        userId: formValue.userId!,
        userName: '',
        jobId: formValue.jobId!,
        jobName: '',
        workedDate: formatLocaleToIsoDate(formValue.workedDate),
        workedHours: formValue.workedHours,
      };

      if (this.isEditMode) {
        this.updateTimesheet(timesheet);
      } else {
        this.createTimesheet(timesheet);
      }
    }
  }

  private createTimesheet(timesheet: Timesheet) {
    this.saving.set(true);

    console.log('createSprint - Timesheet:', timesheet);

    this.timesheetService.createTimesheet(timesheet).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Timesheet created successfully:', response);

        this.notificationService.notifySuccess('Timesheet created successfully.');
        this.router.navigate(['/timesheets']);
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error creating timesheet');
      },
    });
  }

  private updateTimesheet(timesheet: Timesheet) {
    this.saving.set(true);

    console.log('updateSprint - Timesheet:', timesheet);

    this.timesheetService.updateTimesheet(timesheet).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Timesheet updated successfully:', response);

        this.notificationService.notifySuccess('Timesheet updated successfully.');
        this.router.navigate(['/timesheets']);
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error updating timesheet');
      },
    });
  }
}
