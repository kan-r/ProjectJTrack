import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification-service';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);

  return next(req).pipe(
    catchError((response: HttpErrorResponse) => {
      console.error(response);

      let errorMessage =
        response?.error.message || response?.message || 'An unexpected application error occurred.';

      notificationService.notifyError(errorMessage);
      return throwError(() => new Error(errorMessage));
    }),
  );
};
