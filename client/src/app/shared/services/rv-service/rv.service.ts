import { Injectable } from '@angular/core';
import { RV } from '../../models/index';
import { Http, Response } from '@angular/http';
import { Service } from '../service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import * as api from '../api';

@Injectable()
export class RVService extends Service<RV> {

  constructor(http: Http) {
    super(http);
  }

  getByID(id: number): Observable<RV> {
      return this.http.get(api.RUTASDEVIAJE + id)
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  get(): Observable<RV[]> {
      return this.http.get(api.RUTASDEVIAJE)
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  create(rv: RV): Observable<void> {
      return this.http.post(api.RUTASDEVIAJE, rv)
                      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
      return this.http.delete(api.RUTASDEVIAJE + id)
                      .catch(this.handleError);
  }

  edit(rv: RV): Observable<void> {
      return null;
  }

}
