import { Component, signal } from '@angular/core';
import { Toolbar } from './layout/toolbar/toolbar';
import { MainLayout } from './layout/main-layout/main-layout';
import { NotificationToast } from "./shared/notification/notification-toast/notification-toast";

@Component({
  selector: 'app-root',
  imports: [Toolbar, MainLayout, NotificationToast],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('jtrack-app');
}
