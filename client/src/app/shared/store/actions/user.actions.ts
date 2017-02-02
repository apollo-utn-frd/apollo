
import { Action } from '@ngrx/store';
import {User} from "../../models/user";
import {UserWithAuthVM} from "../../../login/storeToUserWithAuth.vm";
import {UserFormVM} from "../../models/userForm.vm";

export const USER_SAVE_ACTION = 'USER_SAVE_ACTION';
export const USER_UPDATE_ACTION = 'USER_UPDATE_ACTION';
export const USER_EDIT_ACTION = 'USER_EDIT_ACTION';

export class SaveUserAction implements Action {
  readonly type = USER_SAVE_ACTION;
  constructor(public payload?: User) {}
}

export class UpdateUserAction implements Action {
  readonly type = USER_UPDATE_ACTION;
  constructor(public payload?: {user: UserWithAuthVM, form: UserFormVM}) { }
}

export class EditedUserAction implements Action {
  readonly type = USER_EDIT_ACTION;
  constructor(public payload?: any) {}
}
