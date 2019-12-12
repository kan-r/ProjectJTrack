import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  currentUser: string = "";

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.currentUserObservable
      .subscribe(
        (user: string) => this.currentUser = user.toUpperCase()
      );
  }
}