import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { firstValueFrom, of, throwError } from 'rxjs';
import { NotificationService } from '../../../shared/notification/notification-service/notification-service';
import { httpErrorInterceptor } from './http-error-interceptor';

describe('httpErrorInterceptor', () => {
  let notificationServiceMock: { notifyError: ReturnType<typeof vi.fn> };
  
  const request = new HttpRequest('GET', '/api/test');

  const interceptor: HttpInterceptorFn = (req, next) =>
    TestBed.runInInjectionContext(() => httpErrorInterceptor(req, next));

  beforeEach(() => {
    notificationServiceMock = { notifyError: vi.fn() };

    TestBed.configureTestingModule({
      providers: [{ provide: NotificationService, useValue: notificationServiceMock }],
    });

    vi.spyOn(console, 'error').mockImplementation(() => {});
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should pass through a successful response untouched', async () => {
    const next: HttpHandlerFn = () => of({ type: 4 } as any);

    const event = await firstValueFrom(interceptor(request, next));

    expect(event).toEqual({ type: 4 });
    expect(notificationServiceMock.notifyError).not.toHaveBeenCalled();
  });

  it('should notify with the error.error.message when available', async () => {
    const response = new HttpErrorResponse({ error: { message: 'Server exploded' } });
    const next: HttpHandlerFn = () => throwError(() => response);

    await expect(firstValueFrom(interceptor(request, next))).rejects.toThrow('Server exploded');
    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith('Server exploded');
  });

  it('should fall back to response.message when error.message is missing', async () => {
    const response = new HttpErrorResponse({ error: {}, statusText: 'Not Found', status: 404 });
    const next: HttpHandlerFn = () => throwError(() => response);

    await expect(firstValueFrom(interceptor(request, next))).rejects.toThrow(response.message);
    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith(response.message);
  });

  it('should fall back to a default message when neither error.message nor message are present', async () => {
    const response = { error: {} } as HttpErrorResponse;
    const next: HttpHandlerFn = () => throwError(() => response);

    await expect(firstValueFrom(interceptor(request, next))).rejects.toThrow(
      'An unexpected application error occurred.',
    );
    expect(notificationServiceMock.notifyError).toHaveBeenCalledWith(
      'An unexpected application error occurred.',
    );
  });

  it('should log the error response to the console', async () => {
    const response = new HttpErrorResponse({ error: { message: 'Boom' } });
    const next: HttpHandlerFn = () => throwError(() => response);

    await expect(firstValueFrom(interceptor(request, next))).rejects.toThrow();
    expect(console.error).toHaveBeenCalledWith(response);
  });
});
