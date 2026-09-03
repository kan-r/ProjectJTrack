import { Service, signal } from '@angular/core';

export interface Notification {
  id: number;
  message: string;
  type: 'error' | 'success' | 'info';
}

@Service()
export class NotificationService {
  private notificationsState = signal<Notification[]>([]);

  private readonly DEFAULT_DURATION_MS = 1500;
  
  notifications = this.notificationsState.asReadonly();

  notifyError(message: string): void {
    this.notify('error', message, 0);
  }

  notifySuccess(message: string): void {
    this.notify('success', message, this.DEFAULT_DURATION_MS);
  }

  notifyInfo(message: string): void {
    this.notify('info', message, this.DEFAULT_DURATION_MS);
  }

  clear(id: number): void {
    this.notificationsState.update((prev) => prev.filter((alert) => alert.id !== id));
  }

  private notify(type: 'error' | 'success' | 'info', message: string, durationMs: number): void {
    const id = Date.now();
    const newAlert: Notification = { id, message, type };

    // Add to active stack
    this.notificationsState.update((prev) => [...prev, newAlert]);

    // Automatically remove after expiration
    if (durationMs > 0) {
      setTimeout(() => this.clear(id), durationMs);
    }
  }
}
