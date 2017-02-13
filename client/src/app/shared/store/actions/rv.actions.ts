
import '@ngrx/core/add/operator/select';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/let';
import {RVFormVM} from "../../models/rvForm.vm";
import {Point} from "../../models/point";
import {AuthState} from "../state/auth.state";
import {RV} from "../../models/rv";

/* Tipos de las acciones, son utilizados para matchear en el reducer de rutas de viaje */
export const RV_CREATE_ACTION = 'RV_CREATE_ACTION';
export const RV_SHARE_ACTION = 'RV_SHARE_ACTION';
export const RV_FAV_ACTION = 'RV_FAV_ACTION';
export const RV_NEW_COMMENT_ACTION = 'RV_NEW_COMMENT_ACTION';

/* Accion que representa la creacion de una ruta de viaje.
 * Acarrea los datos necesarios para crear una ruta de viaje.
 * Se lanza al enviar el formulario de creacion de ruta de viaje.
 */
export class CreateRVAction {
  readonly type = RV_CREATE_ACTION;
  constructor(public payload?: {rvForm: RVFormVM, sitios: Point[], authData: AuthState}) { }
}

/* Accion que representa el acto de compartir una ruta de viaje
 * Acarrea la RV a ser compartida.
 * Se lanza al ser compartida en la home, mediante un click
 * al icono de compartir en la preview de una ruta de viaje
 * o en la vista de una ruta de viaje.
 */
export class ShareRVAction {
  readonly type = RV_SHARE_ACTION;
  constructor(public payload?: RV) {}
}

/* Accion que representa el hecho de que el usuario ha marcado como favorita
*  una ruta de viaje.
*  Acarrea la ruta de viaje a marcar como favorita
*  */
export class FavRVAction {
  readonly type = RV_FAV_ACTION;
  constructor(public payload?: RV) {}
}

/* Accion que representa la creacion de un nuevo comentario.
 * Acarrea el texto del comentario y la ruta de viaje donde se
 * realiza el comentario*
 */
export class NewCommentAction {
  readonly type = RV_NEW_COMMENT_ACTION;
  constructor(public payload?: {contenido: {contenido: string}, rv: RV}) {}
}
