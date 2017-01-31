import { Action } from '@ngrx/store';
import {User} from "../../models/user";
import {UserFormVM} from "../../models/userForm.vm";

export const USER_SAVE_ACTION = 'USER_SAVE_ACTION';
export const USER_UPDATE_ACTION = 'USER_UPDATE_ACTION';

export class SaveUserAction implements Action {
  readonly type = USER_SAVE_ACTION;
  constructor(public payload?: User) {}
}

export class UpdateUserInfo implements Action {
  readonly type = USER_UPDATE_ACTION;
  constructor(payload?: UserFormVM) { }
}
