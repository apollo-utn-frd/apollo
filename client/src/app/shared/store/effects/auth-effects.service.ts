
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {AuthService} from "../../services/auth/auth.service";
import {Action, Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {AUTH_LOGIN_ACTION, SaveAuthStateAction} from "../actions/auth.actions";
import {toLocalStorage} from "./toLocalStorage";
import {SaveUserAction} from "../actions/user.actions";
import {ApplicationState} from "../state/application.state";
import {go} from "@ngrx/router-store";

@Injectable()
export class AuthEffectService {

  constructor( private actions$: Actions
    , private authService: AuthService
    , private store: Store<ApplicationState>
  ) { }

  @Effect()
  usersLogin$: Observable<Action> = this.actions$
    .ofType(AUTH_LOGIN_ACTION)
    .debug("A user has logged in...")
    .switchMap(action => this.authService.login(action.payload.token))
    .do(toLocalStorage)
    .debug("Retrieving user information")
    .map(userData => {
      this.store.dispatch(new SaveAuthStateAction({id: userData.user.id, token: userData.token}));
      //this.store.dispatch(go('/login'));
      return new SaveUserAction(userData.user);
    })
    .debug("user saved");
}
