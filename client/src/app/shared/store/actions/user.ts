import { Action } from '@ngrx/store';
import { type } from '../util';

export const ActionTypes = {
    LOGIN:  type('[User] Login'),
    LOGOUT: type('[User] Logout')
};

export class LoginAction implements Action {
    type = ActionTypes.LOGIN;

    constructor(public payload: [string, any]) { }
}

export class LogoutAction implements Action {
    type = ActionTypes.LOGOUT;

    constructor() { }
}

export type Actions
    = LoginAction
    | LogoutAction;
