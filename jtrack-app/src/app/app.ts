import { Component, signal } from '@angular/core';
import { Toolbar } from './core/layout/toolbar/toolbar';
import { MainLayout } from './core/layout/main-layout/main-layout';
import { NotificationToast } from "./core/layout/notification-toast/notification-toast";

@Component({
  selector: 'app-root',
  imports: [Toolbar, MainLayout, NotificationToast],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('jtrack-app');
}
