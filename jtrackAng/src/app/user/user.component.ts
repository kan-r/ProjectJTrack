import { Component, OnInit } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['../css/jtrack.css']
  // styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  userList: User[];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getUserList().subscribe(data => this.userList = data);
  }

  deleteUser(userId: string): void {
    this.userService.deleteUser(userId)
      .subscribe(
        _ => {this.userService.getUserList().subscribe(data => this.userList = data);}
      );
  }

}
