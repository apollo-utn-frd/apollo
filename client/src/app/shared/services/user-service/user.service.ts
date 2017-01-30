import { Injectable } from '@angular/core';
import { User, UserForm, RV, Comment } from '../../models/index';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Service } from '../service';
import { Store } from '@ngrx/store';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import * as api from '../api';
import {ApplicationState} from "../../store/state/application.state";

@Injectable()
export class UserService extends Service<User> {

  constructor(http: Http, store: Store<ApplicationState>) {
    super(http, store);
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

  edit(u: User): Observable<void> {
    return this.http.put(api.USUARIOS, JSON.stringify(u), {headers: this.headers})
      .catch(this.handleError);
  }

  share(rv: RV) {
    return this.http.post(api.COMPARTIR_RV + rv.id, rv, {headers: this.headers})
      .catch(this.handleError);
  }

  unshare(rv: RV) {
    return this.http.delete(api.COMPARTIR_RV + rv.id, {headers: this.headers})
      .catch(this.handleError);
  }

  fav(rv: RV) {
    return this.http.post(api.FAVEAR_RV + rv.id, rv, {headers: this.headers})
      .catch(this.handleError);
  }

  unfav(rv: RV) {
    return this.http.delete(api.FAVEAR_RV + rv.id, {headers: this.headers})
      .catch(this.handleError);
  }

  follow(user: User) {
    return this.http.post(api.SEGUIR_USUARIO + user.idGoogle, user, {headers: this.headers})
      .catch(this.handleError);
  }

  unfollow(user: User) {
    return this.http.delete(api.SEGUIR_USUARIO + user.idGoogle, {headers: this.headers})
      .catch(this.handleError);
  }

  comment(rv: RV, comment: Comment) {
    return this.http.post(api.COMENTAR_RV + rv.id, comment, {headers: this.headers})
      .catch(this.handleError);
  }

  getComments() {
    return this.http.get(api.COMENTAR_RV, {headers: this.headers})
      .flatMap((res: Response) => res.json())
      .catch(this.handleError);
  }

  uncomment(comment: Comment) {
    return this.http.delete(api.COMENTAR_RV, {headers: this.headers})
      .catch(this.handleError);
  }

  update(u: User, form: UserForm): void {
    u.nombre = form.nombre;
    u.apellido = form.apellido;
    u.username = form.username;
    u.descripcion = form.descripcion;
  }
}
