import './polyfills.ts';

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { environment } from './environments/environment';
import { AppModule } from './app/';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/skip';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/withLatestFrom';
import 'rxjs/add/operator/reduce';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs';

Observable.prototype.debug = function(message: string) {
  return this.do(
    (nextValue: any) => {
      if (environment.debuggerActivated) {
        console.log(message, nextValue);
      }
    },
    (error: any) => {
      if (environment.debuggerActivated) {
        console.error(message, error);
      }
    },
    () => {
      if (environment.debuggerActivated) {
        console.error("Observable completed - ", message);
      }
    }
  );
};

declare module 'rxjs/Observable' {
  interface Observable<T> {
    debug: (...any: any[]) => Observable<T>
  }
}

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule);
