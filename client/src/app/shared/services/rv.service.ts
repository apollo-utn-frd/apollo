import { Injectable } from '@angular/core';
import { RV } from '../models/index';
import { Response } from '@angular/http';
import { Service } from './service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import { Store } from '@ngrx/store';
import * as api from './api';
import {ApplicationState} from "../store/state/application.state";
import {RVDataVM} from "../models/rvData.vm";
import {AuthState} from "../store/state/auth.state";

@Injectable()
export class RVService extends Service<RV> {
  token: string;

  constructor(http: Http, store: Store<ApplicationState>) {
    super(http, store);
    this.store.select(state => state.authState)
      .subscribe((authState: AuthState) => {
        this.token = authState.token;
      })
  }

  getByID(id: number): Observable<RV> {
      return this.http.get(api.RUTADEVIAJE + id, {headers: this.headers})
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  get(): Observable<RV> {
      return this.http.get(api.RUTADEVIAJE, {headers: this.headers})
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  create(rv: RVDataVM): Observable<Response> {
      return this.http.post(api.RUTADEVIAJE, rv, {headers: this.mkHeaders(this.token)})
                      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
      return this.http.delete(api.RUTADEVIAJE + id, {headers: this.headers})
                      .catch(this.handleError);
  }

}
