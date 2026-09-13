import { signal } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { Toolbar } from './toolbar';
import { AuthService } from '../../core/auth/auth-service/auth-service';

describe('Toolbar', () => {
  let component: Toolbar;
  let fixture: any;
  let isAuthenticated: ReturnType<typeof signal<boolean>>;
  let currentUser: ReturnType<typeof signal<{ fullName?: string } | null>>;

  let authService: {
    isAuthenticated: typeof isAuthenticated;
    currentUser: typeof currentUser;
    login: ReturnType<typeof vi.fn>;
    logout: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    isAuthenticated = signal(false);
    currentUser = signal<{ fullName?: string } | null>(null);

    authService = {
      isAuthenticated,
      currentUser,
      login: vi.fn(),
      logout: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [Toolbar],
      providers: [provideRouter([]), { provide: AuthService, useValue: authService }],
    }).compileComponents();

    fixture = TestBed.createComponent(Toolbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize unauthenticated state by default', () => {
    expect(component.isAuthenticated).toBe(false);
    expect(component.userName).toBeUndefined();
  });

  it('should react to auth signal changes from the AuthService', () => {
    authService.isAuthenticated.set(true);
    authService.currentUser.set({ fullName: 'Alice Admin' });
    fixture.detectChanges();

    expect(component.isAuthenticated).toBe(true);
    expect(component.userName).toBe('Alice Admin');
  });

  it('should delegate login to the auth service', () => {
    component.login();
    expect(authService.login).toHaveBeenCalledTimes(1);
  });

  it('should delegate logout to the auth service', () => {
    component.logout();
    expect(authService.logout).toHaveBeenCalledTimes(1);
  });
});
