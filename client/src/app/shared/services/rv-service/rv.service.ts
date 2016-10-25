import { Injectable } from '@angular/core';
import { RV } from '../../models/index';
import { Http, Response } from '@angular/http';
import { Service } from '../service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'; 
import 'rxjs/add/operator/catch';

@Injectable()
export class RVService extends Service<RV> {

  constructor(http: Http) { 
    super(http);
  }

  getByID(id: number): Observable<RV> {
      return this.http.get("url" + id)
                      .map((res: Response) => res.json() as RV)
                      .catch(this.handleError);
  }

  get(): Observable<RV[]> {
      return this.http.get("url")
                      .map((res: Response) => res.json() as RV[])
                      .catch(this.handleError);
  }

  create(rv: RV): Observable<void> {
      return this.http.post("url", rv)
                      .catch(this.handleError);
  }
  
  delete(id: number): Observable<void> {
      return null;
  }

  edit(id: number, rv: RV): Observable<void> {
      return null;
  }

}
