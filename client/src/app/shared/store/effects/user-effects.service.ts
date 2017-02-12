
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Action} from "@ngrx/store";
import {Observable} from "rxjs";
import {USER_UPDATE_ACTION, UpdateUserAction, EditedUserAction} from "../actions/user.actions";
import {UserService} from "../../services/user.service";
import {UserWithAuthVM} from "../../../login/storeToUserWithAuth.vm";
import {Response} from "@angular/http";
import {go} from "@ngrx/router-store";

@Injectable()
export class UserEffectService {

  constructor( private actions$: Actions
             , private userService: UserService
             ) { }

  @Effect()
  updateUser$: Observable<Action> = this.actions$
    .ofType(USER_UPDATE_ACTION)
    .debug('login form submitted...')
    .switchMap((action: UpdateUserAction) => {
      let userWithAuth: UserWithAuthVM = action.payload.user;
      let form = action.payload.form;
      let modifiedUser =  this.userService.update(userWithAuth.user, form);
      return this.userService.edit(modifiedUser, action.payload.user.token)
    })
    .debug('the user has been edited')
    .do(response => console.log("Respuesta", response))
    .map((res: Response) => new EditedUserAction(res.json()))
    .map(_ => go('/home'))
    .debug('edit finalizado')

}
