import { signal } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import Keycloak, { type KeycloakTokenParsed } from 'keycloak-js';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType } from 'keycloak-angular';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { environment as env } from '../../../../environments/environment';
import { AuthService } from './auth-service';

describe('AuthService', () => {
  let service: AuthService;

  let keycloakMock: Keycloak & {
    login: ReturnType<typeof vi.fn>;
    logout: ReturnType<typeof vi.fn>;
  };

  let keycloakEventSignal: ReturnType<typeof signal>;

  beforeEach(() => {
    keycloakMock = {
      login: vi.fn(),
      logout: vi.fn(),
      tokenParsed: {
        sub: 'user-123',
        preferred_username: 'alice',
        given_name: 'Alice',
        family_name: 'Example',
        name: 'Alice Example',
        email: 'alice@example.com',
        resource_access: {
          [env.keycloak.clientId]: {
            roles: ['admin', 'manager'],
          },
        },
      } as KeycloakTokenParsed,
    } as unknown as Keycloak & {
      login: ReturnType<typeof vi.fn>;
      logout: ReturnType<typeof vi.fn>;
    };

    keycloakEventSignal = signal({ type: KeycloakEventType.Ready, args: false });

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        { provide: Keycloak, useValue: keycloakMock },
        { provide: KEYCLOAK_EVENT_SIGNAL, useValue: keycloakEventSignal },
      ],
    });

    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should populate session data when the Keycloak session is ready', () => {
    keycloakEventSignal.set({ type: KeycloakEventType.Ready, args: true });
    TestBed.tick();

    expect(service.isAuthenticated()).toBe(true);
    expect(service.currentUser()).toEqual({
      id: 'user-123',
      username: 'alice',
      firstName: 'Alice',
      lastName: 'Example',
      fullName: 'Alice Example',
      email: 'alice@example.com',
    });
    expect(service.clientRoles()).toEqual(['admin', 'manager']);
  });

  it('should clear session data and trigger login when the session is not ready', () => {
    keycloakEventSignal.set({ type: KeycloakEventType.Ready, args: false });
    TestBed.tick();

    expect(service.isAuthenticated()).toBe(false);
    expect(service.currentUser()).toBeNull();
    expect(service.clientRoles()).toEqual([]);
    expect(keycloakMock.login).toHaveBeenCalledWith({
      redirectUri: window.location.origin + '/',
    });
  });

  it('should redirect to login on logout event', () => {
    keycloakEventSignal.set({ type: KeycloakEventType.AuthLogout, args: {} });
    TestBed.tick();

    expect(service.isAuthenticated()).toBe(false);
    expect(keycloakMock.login).toHaveBeenCalledWith({
      redirectUri: window.location.origin + '/',
    });
  });

  it('should evaluate role checks from the client roles', () => {
    keycloakEventSignal.set({ type: KeycloakEventType.Ready, args: true });
    TestBed.tick();

    expect(service.hasRole('admin')).toBe(true);
    expect(service.hasRole('manager')).toBe(true);
    expect(service.hasAnyRole(['user', 'editor'])).toBe(false);
    expect(service.hasAnyRole(['user', 'manager'])).toBe(true);
  });

  it('should call logout with the app origin redirect', () => {
    service.logout();

    expect(keycloakMock.logout).toHaveBeenCalledWith({
      redirectUri: window.location.origin,
    });
  });
});
