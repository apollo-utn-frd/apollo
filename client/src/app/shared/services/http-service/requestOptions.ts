import { BaseRequestOptions } from '@angular/http';

export class ReqOptions extends BaseRequestOptions {
    public token: string;

    constructor(options?: any) {
        super();
        let user = JSON.parse(localStorage.getItem('user'));
        this.token = user && user.token;
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Authorization', 'Bearer ' + this.token);
    }
}
