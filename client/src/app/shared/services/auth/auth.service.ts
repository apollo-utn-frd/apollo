import { Injectable } from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import { USUARIOS } from '../api';
import {Store, Action} from '@ngrx/store';
import {SaveAuthStateAction} from "../../store/actions/auth.actions";
import {Observable} from "rxjs";
import {User} from "../../models/user";
import {ApplicationState} from "../../store/state/application.state";

@Injectable()
export class AuthService {

  constructor(private http: Http, private store: Store<ApplicationState>) {}

  login(token: string): Observable<{user: User, token: string}> {
    if (token.length !== 0) {
      let hs: Headers = new Headers();
      hs.append('Content-Type', 'application/json');
      hs.append('Authorization', 'Bearer ' + token);

      return this.http.get(USUARIOS, {headers: hs})
        .map((response: Response) => {
          return {user: response.json(), token: token}
        });
    }
  }
}

