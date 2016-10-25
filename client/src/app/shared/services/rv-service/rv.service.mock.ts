import { Injectable } from '@angular/core';
import { RV } from '../../models/rv';
import { Service } from '../service';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map'; 
import 'rxjs/add/operator/catch';

@Injectable()
export class RVMockService extends Service<RV> {

  constructor(http: Http) { 
    super(http);
  }

  getByID(id: number): Observable<RV> {
      return null;
  }
  
  get(): Observable<RV[]> {
      return null;
  }

  create(rv: RV): Observable<void> {
      return null;
  }

  delete(id: number): Observable<void> {
      return null;
  }

  edit(id: number, rv: RV): Observable<void> {
      return null;
  }

}
