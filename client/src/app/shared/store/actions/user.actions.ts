import { Action } from '@ngrx/store';
import {User} from "../../models/user";

export const USER_SAVE_ACTION = 'USER_SAVE_ACTION';

export class SaveUserAction implements Action {
  readonly type = USER_SAVE_ACTION;
  constructor(public payload?: User) {}
}
