import { Injectable } from '@angular/core';
import { User, UserFormVM, RV} from '../models/index';
import {Http, Response, Headers} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Service } from './service';
import { Store } from '@ngrx/store';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import * as api from './api';
import {ApplicationState} from "../store/state/application.state";
import * as _ from 'lodash';
import {AuthState} from "../store/state/auth.state";

@Injectable()
export class UserService extends Service<User> {

  token: string;
  headers: Headers;

  constructor(http: Http, store: Store<ApplicationState>) {
    super(http, store);
    this.store.select(state => state.authState)
      .subscribe((authState: AuthState) => {
        this.token = authState.token;
        this.headers = this.mkHeaders(this.token);
    });
  }

  getByID(id: number): Observable<User> {
    return this.http.get(api.USUARIOS + id, {headers: this.headers})
      .flatMap((res: Response) => res.json())
      .catch(this.handleError);
  }

  getByName(name: string): Observable<User> {
    return this.http.get(api.USUARIOS + 'u/' + name, {headers: this.headers})
      .flatMap((res: Response) => res.json())
      .catch(this.handleError);
  }

  get(): Observable<User> {
    return this.http.get(api.USUARIOS, {headers: this.headers})
      .map(s => s.json())
      .catch(this.handleError);
  }

  edit(u: User, token: string): Observable<Response> {
    let headers = this.mkHeaders(token);
    console.log(headers);
    return this.http.put(api.USUARIOS, u, {headers: headers})
      .catch(this.handleError);
  }

  unfav(rv: RV) {
    return this.http.delete(api.FAVEAR_RV + rv.id, {headers: this.headers})
      .catch(this.handleError);
  }

  follow(user: User) {
    return this.http.post(api.SEGUIR_USUARIO + user.id, user, {headers: this.headers})
      .catch(this.handleError);
  }

  unfollow(user: User) {
    return this.http.delete(api.SEGUIR_USUARIO + user.id, {headers: this.headers})
      .catch(this.handleError);
  }

  update(u: User, form: UserFormVM): User {
    let clon = _.clone(u);
    clon.nombre = form.nombre;
    clon.apellido = form.apellido;
    clon.username = form.username;
    clon.descripcion = form.descripcion;

    return clon;
  }
}
