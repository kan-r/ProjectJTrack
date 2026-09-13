import { TestBed } from '@angular/core/testing';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { AuthService } from '../auth-service/auth-service';
import { authGuard } from './auth-guard';

describe('authGuard', () => {
  let authService: {
    isAuthenticated: ReturnType<typeof vi.fn>;
    login: ReturnType<typeof vi.fn>;
    hasAnyRole: ReturnType<typeof vi.fn>;
  };
  
  let router: { parseUrl: ReturnType<typeof vi.fn> };

  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => authGuard(...guardParameters));

  beforeEach(() => {
    TestBed.resetTestingModule();

    authService = {
      isAuthenticated: vi.fn(),
      login: vi.fn(),
      hasAnyRole: vi.fn(),
    };
    router = {
      parseUrl: vi.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router },
      ],
    });
  });

  it('should redirect to login and deny access when the user is not authenticated', () => {
    authService.isAuthenticated.mockReturnValue(false);

    const route = {
      data: { roles: ['admin'] },
    } as unknown as ActivatedRouteSnapshot;

    const state = {} as RouterStateSnapshot;

    const result = executeGuard(route, state);

    expect(result).toBe(false);
    expect(authService.login).toHaveBeenCalledTimes(1);
    expect(router.parseUrl).not.toHaveBeenCalled();
  });

  it('should allow access when no roles are required', () => {
    authService.isAuthenticated.mockReturnValue(true);

    const route = {
      data: {},
    } as ActivatedRouteSnapshot;

    const state = {} as RouterStateSnapshot;

    const result = executeGuard(route, state);

    expect(result).toBe(true);
    expect(authService.hasAnyRole).not.toHaveBeenCalled();
  });

  it('should allow access when the user has at least one required role', () => {
    authService.isAuthenticated.mockReturnValue(true);
    authService.hasAnyRole.mockReturnValue(true);

    const requiredRoles = ['admin', 'manager'];

    const route = {
      data: { roles: requiredRoles },
    } as unknown as ActivatedRouteSnapshot;

    const state = {} as RouterStateSnapshot;

    const result = executeGuard(route, state);

    expect(result).toBe(true);
    expect(authService.hasAnyRole).toHaveBeenCalledWith(requiredRoles);
  });

  it('should redirect to the unauthorized page when the user lacks the required role', () => {
    const unauthorizedUrl = {} as any;
    authService.isAuthenticated.mockReturnValue(true);
    authService.hasAnyRole.mockReturnValue(false);
    router.parseUrl.mockReturnValue(unauthorizedUrl);

    const route = {
      data: { roles: ['admin'] },
    } as unknown as ActivatedRouteSnapshot;

    const state = {} as RouterStateSnapshot;

    const result = executeGuard(route, state);

    expect(result).toBe(unauthorizedUrl);
    expect(router.parseUrl).toHaveBeenCalledWith('/unauthorized');
  });
});
