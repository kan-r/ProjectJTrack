import { Service, signal, computed, inject, effect } from '@angular/core';
import Keycloak, { KeycloakTokenParsed } from 'keycloak-js';
import { environment as env } from '../../../environments/environment';
import type { User } from '../../features/sprint/models/user';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType, ReadyArgs, typeEventArgs } from 'keycloak-angular';

@Service()
export class AuthService {
  private keycloak = inject(Keycloak);
  private keycloakSignal = inject(KEYCLOAK_EVENT_SIGNAL);

  isAuthenticated = signal<boolean>(false);
  currentUser = signal<User | null>(null);
  clientRoles = signal<string[]>([]);

  constructor() {
    effect(() => {
      const keycloakEvent = this.keycloakSignal();

      if (keycloakEvent.type === KeycloakEventType.Ready) {
        const isAuthenticated = typeEventArgs<ReadyArgs>(keycloakEvent.args);
        this.isAuthenticated.set(isAuthenticated);

        if (isAuthenticated) {
          this.populateSessionData();
        } else {
          this.clearSessionData();
          this.login();
        }
      }

      if (keycloakEvent.type === KeycloakEventType.AuthLogout) {
        this.isAuthenticated.set(false);
        this.login();
      }
    });
  }

  login() {
    this.keycloak.login({
      redirectUri: window.location.origin + '/',
    });
  }

  logout() {
    this.keycloak.logout({
      redirectUri: window.location.origin,
    });
  }

  hasRole = (role: string) => this.hasRoleSignal(role)();
  hasAnyRole = (roles: string[]) => this.hasAnyRoleSignal(roles)();

  private hasRoleSignal = (role: string) => computed(() => this.clientRoles().includes(role));
  private hasAnyRoleSignal = (roles: string[]) => computed(() => roles.some(role => this.clientRoles().includes(role)));

  private populateSessionData(): void {
    const accessToken: KeycloakTokenParsed | undefined = this.keycloak.tokenParsed;

    if (!accessToken) {
      console.warn('User is not authenticated.');
      return;
    }

    this.currentUser.set({
      id: accessToken['sub'] || '',
      username: accessToken['preferred_username'] || '',
      firstName: accessToken['given_name'],
      lastName: accessToken['family_name'],
      fullName: accessToken['name'],
      email: accessToken['email']
    });

    const clientRoles = accessToken.resource_access?.[env.keycloak.clientId]?.roles || [];
    this.clientRoles.set(clientRoles);
  }

  private clearSessionData(): void {
    this.currentUser.set(null);
    this.clientRoles.set([]);
  }
}
