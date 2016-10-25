import { Injectable } from '@angular/core';
import { User } from '../../models/user';
import { Service } from '../service';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';

@Injectable()
export class UserMockService extends Service<User> {

  constructor(http: Http) { 
    super(http);
  }
  
  getByID(id: number): Observable<User> {
    return null;
  }

  get(): Observable<User[]> {
    return null;
  }

  create(u: User): Observable<void> {
    return null;
  }

  delete(id: number): Observable<void> {
    return null; // no esta permitida
  }

  edit(id: number, u: User): Observable<void> {
    return null;
  }
}