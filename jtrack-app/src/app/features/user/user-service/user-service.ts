import { Service } from '@angular/core';
import { environment as env } from '../../../../environments/environment';
import { httpResource } from '@angular/common/http';
import { User } from '../models/user';

@Service()
export class UserService {

  getUsers() {
    return httpResource<User[]>(() => `${env.apiUrl}/users`);
  }
}
