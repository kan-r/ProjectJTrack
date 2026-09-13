import { TestBed } from '@angular/core/testing';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { NotificationService } from '../../../shared/notification/notification-service/notification-service';
import { GlobalErrorHandler } from './global-error-handler';

describe('GlobalErrorHandler', () => {
  let service: GlobalErrorHandler;
  let notificationServiceMock: { notifyError: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    notificationServiceMock = { notifyError: vi.fn() };

    TestBed.configureTestingModule({
      providers: [
        GlobalErrorHandler,
        { provide: NotificationService, useValue: notificationServiceMock },
      ],
    });

    service = TestBed.inject(GlobalErrorHandler);

    vi.spyOn(console, 'error').mockImplementation(() => {});
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should notify with the error message when the error has a message', () => {
    const error = new Error('Something went wrong');

    service.handleError(error);

    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith('Something went wrong');
  });

  it('should notify with a fallback message when the error has no message', () => {
    service.handleError({});

    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith(
      'An unexpected application error occurred.',
    );
  });

  it('should notify with a fallback message when the error is null', () => {
    service.handleError(null);

    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith(
      'An unexpected application error occurred.',
    );
  });

  it('should notify with a fallback message when the error is undefined', () => {
    service.handleError(undefined);

    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith(
      'An unexpected application error occurred.',
    );
  });

  it('should log the error to the console', () => {
    const error = new Error('Logged error');

    service.handleError(error);

    expect(console.error).toHaveBeenCalledWith('Error:', error);
  });
});
