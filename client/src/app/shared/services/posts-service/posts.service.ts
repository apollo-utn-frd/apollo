import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Service } from '../service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import { Store } from '@ngrx/store';
import * as api from '../api';
import {ApplicationState} from "../../store/state/application.state";
import {AuthState} from "../../store/state/auth.state";
import {Post} from "../../models/post";

@Injectable()
export class PostService extends Service<Post> {
  token: string;

  constructor(http: Http, store: Store<ApplicationState>) {
    super(http, store);
    this.store.select(state => state.authState)
      .subscribe((authState: AuthState) => {
        this.token = authState.token;
      })
  }

  get(): Observable<Post[]> {
    return this.http.get(api.POSTS, {headers: this.mkHeaders(this.token)})
      .map((res: Response) => res.json())
      .catch(this.handleError);
  }
}
