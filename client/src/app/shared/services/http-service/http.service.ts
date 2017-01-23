import { Injectable } from '@angular/core';

import {
    Http,
    RequestOptions,
    RequestOptionsArgs,
    Response,
    Request,
    Headers,
    XHRBackend
} from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { ReqOptions } from './requestOptions';

@Injectable()
export class HttpService extends Http {
    public token: string;

    apiUrl = '';

    constructor(backend: XHRBackend, defaultOptions: ReqOptions) {
        super(backend, defaultOptions);
    }

    private getUrl(url: string): string {
        return this.apiUrl + url;
    }

    private requestOptions(options?: RequestOptionsArgs): RequestOptionsArgs {
        if (options == null) {
            options = new ReqOptions();
        }
        if (options.headers == null) {
            options.headers = new Headers();
        }

        return options;
    }
    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        let endpoint = this.getUrl(url);
        let opt = this.requestOptions(options);
        return super.get(endpoint, opt)
                // .catch( funcion para errores  // catch exception here!
                    .do((res: Response) => {// handle success
                    }, (error: any) => { // handle errors
                    })
                    .finally(() => { // request completed
                    });
    }


}
