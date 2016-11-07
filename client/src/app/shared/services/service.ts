import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';

export abstract class Service<T> {

  constructor(protected http: Http) {}

  abstract getByID<T>(id: number): Observable<T>;
  abstract get<T>(): Observable<T[]>;
  abstract create<T>(t: T): Observable<void>;
  abstract delete(id: number): Observable<void>;
  abstract edit<T>(t: T): Observable<void>;

  handleError(error: any) {
    let errorMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errorMsg);
    return Observable.throw(errorMsg);
  }
}
