
import {Post} from "../../models/post";
import {Action} from "@ngrx/store";
import {Comentario} from "../../models/comentario";

/* Tipos de las acciones, son utilizados para matchear en el reducer de uiState.
 * uiState contiene las publicaciones del usuario actual y los comentarios de
 * la ruta de viaje cuya vista se encuentra actualmente activa.
 */
export const UPDATE_POSTS_ACTION = 'UPDATE_POSTS_ACTION';
export const UPDATE_STORED_POSTS_ACTION = 'UPDATE_STORED_POSTS_ACTION';
export const LOAD_COMMENT_ACTION = 'LOAD_COMMENT_ACTION';
export const SAVE_COMMENTS_ACTION = 'SAVE_COMMENTS_ACTION';
export const CLEAN_COMMENTS_ACTION = 'CLEAN_COMMENTS_ACTION';

/* Accion que arroja el efecto que carga todas las publicaciones de un usuario */
export class UpdatePostsAction implements Action {
  readonly type = UPDATE_POSTS_ACTION;
  constructor(public payload?: any) {}
}
/* Accion que guarda en la store los posts de un usuario */
export class UpdateStoredPostsAction implements Action {
  readonly type = UPDATE_STORED_POSTS_ACTION;
  constructor(public payload?: Post[]) {}
}

/* Accion que arroja el efecto que carga un comentario de una ruta de viaje
 * El comentario a cargar es el cual cuya id es la pasada por parametro al
 * constructor de la accion.
 */
export class LoadComment implements Action {
  readonly type = LOAD_COMMENT_ACTION;
  constructor(public payload?: number) {}
}

/* Accion que guarda en la store los comentarios que acarrea la accion.*/
export class SaveCommentsAction implements Action {
  readonly type = SAVE_COMMENTS_ACTION;
  constructor(public payload?: Comentario) {}
}

/* Accion que borra todos los comentarios de la ruta de viaje actual*/
export class CleanCommentsAction implements Action {
  readonly type = CLEAN_COMMENTS_ACTION;
  constructor(public payload?: any) {}
}
