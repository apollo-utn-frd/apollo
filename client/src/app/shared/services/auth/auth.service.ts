import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { USUARIOS } from '../api';
import { Store } from '@ngrx/store';
import { State, LoginAction } from '../../store/index';

@Injectable()
export class AuthService {

  token: string;

  constructor(private http: Http, private store: Store<State>) {}

  login(): void {
    this.token = window.location.hash.substr(1);

    if (this.token.length !== 0) {
      let hs = new Headers();
      hs.append('Content-Type', 'application/json');
      hs.append('Authorization', 'Bearer ' + this.token);

      this.http.get(USUARIOS, {headers: hs})
               .forEach(response => {
                 const json = response.json();
                 this.store.dispatch(new LoginAction([this.token, json]));
                });
    } else {
      console.error('usuario no logueado');
    }
  }

}
