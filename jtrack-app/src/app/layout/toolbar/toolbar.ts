import { Component, effect, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthService } from '../../core/auth/auth-service/auth-service';

@Component({
  imports: [MatToolbarModule, MatButtonModule, MatIconModule],
  selector: 'app-toolbar',
  styleUrl: './toolbar.css',
  templateUrl: './toolbar.html',
})
export class Toolbar {
  private authService = inject(AuthService);

  isAuthenticated: boolean = false;
  userName: string | undefined = '';

  constructor() {
    effect(() => {
      this.isAuthenticated = this.authService.isAuthenticated();
      this.userName = this.authService.currentUser()?.fullName;
    });
  }

  login() {
    this.authService.login();
  }

  logout() {
    this.authService.logout();
  }
}
