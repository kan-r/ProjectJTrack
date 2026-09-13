import { TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NotificationToast } from './notification-toast';
import { NotificationService } from '../notification-service/notification-service';

describe('NotificationToast', () => {
  let component: NotificationToast;

  let notificationService: {
    notifications: ReturnType<typeof vi.fn>;
    clear: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    notificationService = {
      notifications: vi.fn(() => [
        { id: 1, message: 'Saved successfully', type: 'success' },
        { id: 2, message: 'Failed to save', type: 'error' },
      ]),
      clear: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [NotificationToast],
      providers: [{ provide: NotificationService, useValue: notificationService }],
    }).compileComponents();

    const fixture = TestBed.createComponent(NotificationToast);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should inject the notification service and render notifications', () => {
    const notifications = component['notificationService'].notifications();

    expect(notifications).toHaveLength(2);
    expect(notifications[0].type).toBe('success');
    expect(notifications[0].message).toBe('Saved successfully');
    expect(notifications[1].type).toBe('error');
    expect(notifications[1].message).toBe('Failed to save');
  });

  it('should render one toast box per notification', () => {
    const fixture = TestBed.createComponent(NotificationToast);
    fixture.detectChanges();

    const toastBoxes = fixture.debugElement.queryAll(By.css('.jt-toast-box'));
    expect(toastBoxes.length).toBe(2);
  });

  it('should clear an error notification when the close button is clicked', () => {
    const fixture = TestBed.createComponent(NotificationToast);
    fixture.detectChanges();

    const closeButton = fixture.debugElement.query(By.css('button'));
    closeButton.nativeElement.click();

    expect(notificationService.clear).toHaveBeenCalledWith(2);
  });
});
