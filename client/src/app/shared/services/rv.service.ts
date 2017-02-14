import { Injectable } from '@angular/core';
import { RV } from '../models/rv';
import {Response, Headers} from '@angular/http';
import { Service } from './service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/mergeMap';
import {Store, Action} from '@ngrx/store';
import * as api from './api';
import {ApplicationState} from "../store/state/application.state";
import {RVDataVM} from "../models/rvData.vm";
import {AuthState} from "../store/state/auth.state";
import {Comentario} from '../models/comentario'
@Injectable()
export class RVService extends Service<RV> {
  token: string;
  headers: Headers;

  constructor(http: Http, store: Store<ApplicationState>) {
    super(http, store);
    this.store.select(state => state.authState)
      .subscribe((authState: AuthState) => {
        this.token = authState.token;
        this.headers = this.mkHeaders(this.token);
      })
  }

  /* documentacion, revisar metodo */
  getByID(id: number): Observable<RV> {
      return this.http.get(api.RUTADEVIAJE + id, {headers: this.headers})
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  /* metodo para obtener las rutas de viaje, usado para busqueda */
  get(): Observable<RV> {
      let url = api.RUTADEVIAJE + 'search';
      return this.http.get(url, {headers: this.headers})
                      .flatMap((res: Response) => res.json())
                      .catch(this.handleError);
  }

  /* metodo para crear una rv, recibe como parametro los datos de la rv a crear. */
  create(rv: RVDataVM): Observable<Response> {
      return this.http.post(api.RUTADEVIAJE, rv, {headers: this.headers})
                      .catch(this.handleError);
  }

  /* metodo para compartir la ruta de viaje pasada como parametro.
  *  El usuario que la comparte es el actualmente logueado.
  *  No se pueden compartir rutas de viaje propias.
  */
  share(rv: RV): Observable<Response> {
    let url = api.COMPARTIR_RV + rv.id;
    return this.http.post(url, undefined, {headers: this.headers})
      .flatMap((res: Response) => res.json())
  }

  /* Metodo para setear como favorito una ruta de viaje pasada
   * como parametro. No se pueden marcar como favoritas las
   * rutas de viaje propias
   */
  fav(rv: RV): Observable<Response> {
    let url = api.FAVEAR_RV + rv.id;
    return this.http.post(url, undefined, {headers: this.headers})
      .flatMap((res: Response) => res.json());
  }

  /* Metodo para realizar un comentario.
   * Toma como parametro un comentario y una ruta de viaje.
   */
  comment(content: {contenido: string}, rv: RV): Observable<Response> {
    let url = api.COMENTAR_RV + rv.id;
    return this.http.post(url, content, {headers: this.headers})
      .map((res: Response) => res.json());
  }

  getCommentByID(id: number): Observable<Comentario> {
    let url = api.COMENTARIO + id;
    return this.http.get(url, {headers: this.headers})
            .map((res: Response) => res.json());
  }

  /* documentacion, revisar metodo */
  delete(id: number): Observable<void> {
      return this.http.delete(api.RUTADEVIAJE + id, {headers: this.headers})
                      .catch(this.handleError);
  }

}
