
import {Action} from "@ngrx/store";
import {AuthState} from "../state/auth.state";

export const AUTH_LOGIN_ACTION = 'AUTH_LOGIN_ACTION';
export const AUTH_LOGOUT_ACTION = 'AUTH_LOGOUT_ACTION';
export const AUTH_SAVE_ACTION = 'AUTH_SAVE_ACTION';

export class LoginAction implements Action {
  readonly type = AUTH_LOGIN_ACTION;
  constructor(public payload?: AuthState) { }
}

export class LogoutAction implements Action {
  readonly type = AUTH_LOGOUT_ACTION;
  constructor(public payload?: [string, any]) { }
}

export class SaveAuthStateAction implements Action {
  readonly type = AUTH_SAVE_ACTION;
  constructor(public payload?: AuthState) { }
}
