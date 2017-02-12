
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Action, Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {ApplicationState} from "../state/application.state";
import {RV_CREATE_ACTION, CreateRVAction, NewRVAction} from "../actions/rv.actions";
import {RVService} from "../../services/rv.service";
import {User} from "../../models/user";
import {RV} from "../../models/rv";
import {AuthState} from "../state/auth.state";
import {Point} from "../../models/point";
import {RVFormVM} from "../../models/rvForm.vm";
import {RVDataVM} from "../../models/rvData.vm";
import {Response} from "@angular/http";
import {go} from "@ngrx/router-store";
import {SaveUserAction} from "../actions/user.actions";
import {UserService} from "../../services/user.service";


@Injectable()
export class RVEffectService {
  user: User;
  constructor( private actions$: Actions
             , private store: Store<ApplicationState>
             , private rvService: RVService
             , private userService: UserService
             ) {
    this.store.select(state => state.storeData)
      .subscribe(storeData => {
        this.user = storeData.currentUser;
      })
  }

  @Effect()
  createRV$: Observable<Action> = this.actions$
    .ofType(RV_CREATE_ACTION)
    .switchMap((action: CreateRVAction) => {
      let rv: RVDataVM = mkRV(action.payload);
      return this.rvService.create(rv);
    })
    .switchMap(_ => this.userService.get()) // esto esta re mal hahaha, separar en otra accion
    .map((user: User) => this.store.dispatch(new SaveUserAction(user)))
    .debug("Ruta creada")
    .map(_ => go('/home'))
    .debug("Redireccion a /home")

}

export function mkRV(rvData: {rvForm: RVFormVM, sitios: Point[], authData: AuthState}) {
  return {
    nombre: rvData.rvForm.nombre,
    creador: parseInt(rvData.authData.id),
    descripcion: rvData.rvForm.descripcion,
    publico: rvData.rvForm.publico,
    sitios: rvData.sitios
  }
}
