import { Component, inject } from '@angular/core';
import { NotificationService } from '../../services/notification-service';
import { NgClass } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  imports: [NgClass, MatIconModule],
  selector: 'app-notification-toast',
  styleUrl: './notification-toast.css',
  templateUrl: './notification-toast.html',
})
export class NotificationToast {
  protected notificationService = inject(NotificationService);
}
