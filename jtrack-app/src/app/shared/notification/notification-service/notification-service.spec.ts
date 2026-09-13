import { TestBed } from '@angular/core/testing';
import { NotificationService } from './notification-service';

describe('NotificationService', () => {
  let service: NotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should add a success notification to the state', () => {
    service.notifySuccess('Saved successfully');

    const notifications = service.notifications();

    expect(notifications).toHaveLength(1);
    expect(notifications[0]).toMatchObject({
      message: 'Saved successfully',
      type: 'success',
    });
  });

  it('should add an error notification to the state without a timeout', () => {
    service.notifyError('Something failed');

    const notifications = service.notifications();

    expect(notifications).toHaveLength(1);
    expect(notifications[0]).toMatchObject({
      message: 'Something failed',
      type: 'error',
    });
  });

  it('should add an info notification to the state', () => {
    service.notifyInfo('Working on it');

    const notifications = service.notifications();

    expect(notifications).toHaveLength(1);
    expect(notifications[0]).toMatchObject({
      message: 'Working on it',
      type: 'info',
    });
  });

  it('should remove a notification by id', () => {
    service.notifySuccess('Saved');
    const [first] = service.notifications();

    service.clear(first.id);

    expect(service.notifications()).toEqual([]);
  });

  it('should auto-clear a timed success notification', async () => {
    vi.useFakeTimers();

    service.notifySuccess('Auto clear');
    const [first] = service.notifications();

    expect(service.notifications()).toHaveLength(1);

    vi.advanceTimersByTime(1500);
    await Promise.resolve();

    expect(service.notifications()).toEqual([]);
    expect(service.notifications().some((notification) => notification.id === first.id)).toBe(false);

    vi.useRealTimers();
  });
});
