import { Injectable } from '@angular/core';
import { RV } from '../../models/index';
import { Response } from '@angular/http';
import { Service } from '../service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import { Store } from '@ngrx/store';
import { State } from '../../store/index';
import * as api from '../api';

@Injectable()
export class RVService extends Service<RV> {

  constructor(http: Http, store: Store<State>) {
    super(http, store);
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

  create(rv: RV): Observable<void> {
      return this.http.post(api.RUTADEVIAJE, rv, {headers: this.headers})
                      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
      return this.http.delete(api.RUTADEVIAJE + id, {headers: this.headers})
                      .catch(this.handleError);
  }

}
