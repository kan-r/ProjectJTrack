import { Component, effect, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthService } from '../../services/auth-service';

@Component({
  imports: [MatToolbarModule, MatButtonModule, MatIconModule],
  selector: 'app-toolbar',
  styleUrl: './toolbar.css',
  templateUrl: './toolbar.html',
})
export class Toolbar {
  private authService = inject(AuthService);

  protected isAuthenticated: boolean = false;
  protected userName: string | undefined = '';

  constructor() {
    effect(() => {
      this.isAuthenticated = this.authService.isAuthenticated();
      this.userName = this.authService.currentUser()?.fullName;
    });
  }

  protected login() {
    this.authService.login();
  }

  protected logout() {
    this.authService.logout();
  }
}
