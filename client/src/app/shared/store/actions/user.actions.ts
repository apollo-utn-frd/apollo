
import { Action } from '@ngrx/store';
import {User} from "../../models/user";
import {UserWithAuthVM} from "../../../login/storeToUserWithAuth.vm";
import {UserFormVM} from "../../models/userForm.vm";

/* Tipos de las acciones, son utilizados para matchear en el reducer de storeData.
 * storeData contiene el estado del usuario actual (currentUser)
 */
export const USER_SAVE_ACTION = 'USER_SAVE_ACTION';
export const USER_UPDATE_ACTION = 'USER_UPDATE_ACTION';

/* Accion que representa el hecho de guardar el usuario que se ha editado, reemplazando el estado
 * del usuario actual en la store. A diferencia de UpdateUserAction, este no es un efecto,
 * no realiza llamadas http, sino que solo actualiza el estado en la store.
 * Se debe lanzar siempre tras editar el estado del usuario en el backend, para mantener
 * consistencia con la store (frontend).
 * Acarrea el usuario ya modificado.
 */
export class SaveUserAction implements Action {
  readonly type = USER_SAVE_ACTION;
  constructor(public payload?: User) {}
}

/* Accion que representa la modificacion de alguno de los atributos del usuario actual.
 * Se lanza al enviar el formulario de la pantalla /login, en el primer logueo a Apollo.
 * Acarrea los datos tomados del formulario de login y los datos de logueo del usuario.
 */
export class UpdateUserAction implements Action {
  readonly type = USER_UPDATE_ACTION;
  constructor(public payload?: {user: UserWithAuthVM, form: UserFormVM}) { }
}
