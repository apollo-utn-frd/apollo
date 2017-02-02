
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Action, Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {USER_UPDATE_ACTION, UpdateUserAction, EditedUserAction} from "../actions/user.actions";
import {ApplicationState} from "../state/application.state";
import {go} from "@ngrx/router-store";
import {UserService} from "../../services/user-service/user.service";
import {User} from "../../models/user";
import {UserWithAuthVM} from "../../../login/storeToUserWithAuth.vm";
import {Response} from "@angular/http";


@Injectable()
export class UserEffectService {

  constructor( private actions$: Actions
             , private userService: UserService
             , private store: Store<ApplicationState>
             ) { }

  @Effect({dispatch: false})
  updateUser$: Observable<Action> = this.actions$
    .ofType(USER_UPDATE_ACTION)
    .filter((mvida: UpdateUserAction) => !mvida.payload)
    .debug('login form submitted...')
    .switchMap((action: UpdateUserAction) => {
      let userWithAuth: UserWithAuthVM = action.payload.user;
      let form = action.payload.form;
      let modifiedUser =  this.userService.update(userWithAuth.user, form);
      return this.userService.edit(modifiedUser, action.payload.user.token)
    })
    .debug('the user has been edited')
    .map((res: Response) => new EditedUserAction({payload: res.json()}))
    .debug('edit finalizado')

}
