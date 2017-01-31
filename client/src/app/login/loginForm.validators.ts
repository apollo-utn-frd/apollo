import {ValidatorFn} from "@angular/forms";

export interface LoginFormValidators {
  name: ValidatorFn | ValidatorFn[],
  username: ValidatorFn | ValidatorFn[],
  description: ValidatorFn | ValidatorFn[]
}
