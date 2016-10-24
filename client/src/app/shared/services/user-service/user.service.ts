import { Injectable } from '@angular/core';
import { User } from '../../models/user';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Service } from '../service.interface';

@Injectable()
export class UserService implements Service {

  getByID<User>(id: number): User {
    return null;
  }

  get<User>(): User[] {
    return null;
  }

  create<User>(t: User): void {
    return null;
  }

  delete(id: number): void {
    return null;
  }

  edit<User>(id: number, u: User): void {
    return null;
  }
}