import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class AuthService {

  url_usuario = 'http://localhost:8080/usuario';
  auth_url = 'localhost:8080/auth/google';
  login_url = '/login';

  token: string;

  constructor(private http: Http) {}

  login() {
    this.token = window.location.hash.substr(1);

    if (this.token.length !== 0) {
      console.log('Usuario logueado, token: ' + this.token);
      let hs = new Headers();
      hs.append('Content-Type', 'application/json');
      hs.append('Authorization', 'Bearer ' + this.token);
      let opts = { headers: hs}; // agrego headers

      // hago get
      this.http.get(this.url_usuario, opts)
               .forEach(value => console.log(value));
    } else {
      console.error('usuario no logueado');
    }
  }
}