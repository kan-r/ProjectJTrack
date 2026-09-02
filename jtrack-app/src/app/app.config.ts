import { ApplicationConfig, ErrorHandler, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { MAT_DATE_LOCALE, provideNativeDateAdapter } from '@angular/material/core';

import { routes } from './app.routes';
import { GlobalErrorHandler } from './core/error-handers/global-error-handler';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { httpErrorInterceptor } from './core/error-handers/http-error-interceptor';
import { environment as env } from '../environments/environment';

import { 
  provideKeycloak, 
  withAutoRefreshToken, 
  createInterceptorCondition,
  includeBearerTokenInterceptor,
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  AutoRefreshTokenService,
  UserActivityService
} from 'keycloak-angular';

// Attach the bearer token only to requests targeting our own API
const bearerTokenCondition = createInterceptorCondition({
  urlPattern: new RegExp(`^${env.apiUrl}(/.*)?$`, 'i'),
  bearerPrefix: 'Bearer'
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    
    provideBrowserGlobalErrorListeners(),

    { 
      provide: ErrorHandler, 
      useClass: GlobalErrorHandler 
    },

    provideNativeDateAdapter(),

    { 
      provide: MAT_DATE_LOCALE, 
      useValue: 'en-AU' 
    },

    provideHttpClient(withInterceptors([
      httpErrorInterceptor,
      includeBearerTokenInterceptor
    ])),

    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [bearerTokenCondition]
    },

    // Modern Functional Keycloak Initialization with PKCE
    provideKeycloak({
      config: {
        url: env.keycloak.url,
        realm: env.keycloak.realm,
        clientId: env.keycloak.clientId
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        pkceMethod: 'S256'
      },
      features: [
        withAutoRefreshToken({
          onInactivityTimeout: 'logout',
          sessionTimeout: 600000
        })
      ],
      providers: [AutoRefreshTokenService, UserActivityService]
    })
  ]
};

