import '@ngrx/core/add/operator/select';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/let';
import {AuthState} from "../state/auth.state";
import {Point} from "../../models/point";
import {RVFormVM} from "../../models/rvForm.vm";

export const RV_CREATE_ACTION = 'RV_CREATE_ACTION';
export const RV_NEW_ACTION = 'RV_NEW_ACTION';

export class CreateRVAction {
  readonly type = RV_CREATE_ACTION;
  constructor(public payload?: {rvForm: RVFormVM, sitios: Point[], authData: AuthState}) { }
}

export class NewRVAction {
  readonly type = RV_NEW_ACTION;
  constructor(public payload?: any) { }
}
