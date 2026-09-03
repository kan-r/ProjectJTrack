import { ErrorHandler, inject, Service } from '@angular/core';
import { NotificationService } from '../../../shared/notification/notification-service/notification-service';

@Service()
export class GlobalErrorHandler implements ErrorHandler {
  private notificationService = inject(NotificationService);

  handleError(error: any): void {
    console.error('Error:', error);

    const message = error?.message || 'An unexpected application error occurred.';
    this.notificationService.notifyError(message);
  }
}
