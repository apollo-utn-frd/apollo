import { Observable } from 'rxjs/Observable';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/observable/throw';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../store/state/application.state";

export abstract class Service<T> {

  constructor(public http: Http, public store: Store<ApplicationState>) { }

  handleError(error: any) {
    let errorMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Observable.throw(errorMsg);
  }

  mkHeaders(token: string): Headers {
    let headers: Headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', 'Bearer ' + token);

    return headers;
  }
}
