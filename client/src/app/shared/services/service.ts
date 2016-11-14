import { Observable } from 'rxjs/Observable';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/observable/throw';
import { Store } from '@ngrx/store';
import { getToken, State } from '../store/index';

export abstract class Service<T> {
  public token: string;
  public headers: Headers = new Headers();

  constructor( protected http: Http
             , protected store: Store<State>
             ) {
               store.let(getToken)
                    .subscribe(
                      t => {
                        this.token = t;
                        this.headers.append('Content-Type', 'application/json');
                        this.headers.append('Authorization', 'Bearer ' + this.token);
                      },
                      e => console.error(e),
                      () => console.log('complete')
                    );
             }

  abstract getByID<T>(id: number): Observable<T>;
  abstract get<T>(): Observable<T>;

  handleError(error: any) {
    let errorMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Observable.throw(errorMsg);
  }
}
