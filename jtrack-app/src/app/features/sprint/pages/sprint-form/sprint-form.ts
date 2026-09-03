import { Component, effect, inject, signal } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Sprint } from '../../models/sprint';
import { CommonModule } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { SprintService } from '../../sprint-service/sprint-service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SprintStatus } from '../../models/sprint-status';
import { formatLocaleToIsoDate } from '../../../../shared/utils/date-utils';
import { NotificationService } from '../../../../shared/notification/notification-service/notification-service';

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
  selector: 'app-sprint-form',
  styleUrl: './sprint-form.css',
  templateUrl: './sprint-form.html',
})
export class SprintForm {
  private sprintService = inject(SprintService);
  private notificationService = inject(NotificationService);

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  isEditMode: boolean = false;
  pageTitle: string = 'Create Sprint';

  saving = signal(false);
  private sprintId = signal<number | null>(null);

  sprintStatusesResource = this.sprintService.getSprintStatuses();
  sprintStatuses: SprintStatus[] = [];

  sprintResource = this.sprintService.getSprintById(this.sprintId);

  sprintForm: FormGroup = this.fb.group({
    id: [null],
    name: ['', [Validators.required]],
    statusCode: [null],
    startDate: [null],
    endDate: [null],
  });

  constructor() {
    effect(() => {
      if (this.sprintStatusesResource.hasValue()) {
        this.sprintStatuses = this.sprintStatusesResource.value();
      }

      if (!this.sprintResource.hasValue()) {
        return;
      }

      const sprint = this.sprintResource.value();

      if (sprint) {
        this.sprintForm.patchValue({
          id: sprint.id,
          name: sprint.name,
          statusCode: sprint.statusCode,
          startDate: sprint.startDate ? new Date(sprint.startDate) : null,
          endDate: sprint.endDate ? new Date(sprint.endDate) : null,
        });
      }
    });
  }

  ngOnInit() {
    const sprintId = this.route.snapshot.paramMap.get('id');

    console.log('ngOnInit - sprintId:', sprintId);

    if (sprintId) {
      this.isEditMode = true;
      this.pageTitle = 'Edit Sprint';
      this.sprintId.set(parseInt(sprintId));
    }
  }

  onSubmit() {
    console.log('onSubmit - Form:', this.sprintForm.value);

    if (this.sprintForm.valid) {
      const formValue = this.sprintForm.value;

      const sprint: Sprint = {
        id: formValue.id!,
        name: formValue.name!,
        statusCode: formValue.statusCode!,
        statusDescription: '',
        startDate: formatLocaleToIsoDate(formValue.startDate),
        endDate: formatLocaleToIsoDate(formValue.endDate),
      };

      if (this.isEditMode) {
        this.updateSprint(sprint);
      } else {
        this.createSprint(sprint);
      }
    }
  }

  private createSprint(sprint: Sprint) {
    this.saving.set(true);

    console.log('createSprint - Sprint:', sprint);

    this.sprintService.createSprint(sprint).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Sprint created successfully:', response);

        this.notificationService.notifySuccess('Sprint created successfully.');
        this.router.navigate(['/sprints']);
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error creating sprint');
      },
    });
  }

  private updateSprint(sprint: Sprint) {
    this.saving.set(true);

    console.log('updateSprint - Sprint:', sprint);

    this.sprintService.updateSprint(sprint).subscribe({
      next: (response) => {
        this.saving.set(false);
        console.log('Sprint updated successfully:', response);

        this.notificationService.notifySuccess('Sprint updated successfully.');
        this.router.navigate(['/sprints']);
      },
      error: (error) => {
        this.saving.set(false);
        console.error('Error updating sprint');
      },
    });
  }
}
