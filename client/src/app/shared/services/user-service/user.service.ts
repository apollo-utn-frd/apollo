import { Injectable } from '@angular/core';
import { User } from '../../models/user';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Service } from '../service';
import 'rxjs/add/operator/map'; 
import 'rxjs/add/operator/catch';

@Injectable()
export class UserService extends Service<User> {

  constructor(http: Http) { 
    super(http);
  }
  
  getByID(id: number): Observable<User> {
    return this.http.get("url" + id)
                    .map((res: Response) => res.json() as User)
                    .catch(this.handleError);
  }

  get(): Observable<User[]> {
    return this.http.get("url")
                    .map((res: Response) => res.json() as User[])
                    .catch(this.handleError);
  }

  create(u: User): Observable<void> {
    return this.http.post("url", u)
                    .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
    return null; // no esta permitida
  }

  edit(id: number, u: User): Observable<void> {
    return null;
  }
}