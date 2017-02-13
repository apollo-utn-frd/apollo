
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Action, Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {ApplicationState} from "../state/application.state";
import {
  RV_CREATE_ACTION, CreateRVAction, RV_SHARE_ACTION, ShareRVAction, RV_FAV_ACTION,
  FavRVAction
} from "../actions/rv.actions";
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

  /* Efecto que triggerea la creacion de una ruta de viaje:
   * Reune los datos necesarios para crear la RV
   * Hace la llamada http correspondiente
   * Actualiza el usuario en la store (frontend)
   * Redirecciona al usuario a la pantalla /home
   */
  @Effect() createRV$: Observable<Action> = this.actions$
    .ofType(RV_CREATE_ACTION)
    .switchMap((action: CreateRVAction) => {
      let rv: RVDataVM = mkRV(action.payload);
      return this.rvService.create(rv);
    })
    .switchMap(_ => this.userService.get()) // esto esta re mal hahaha, separar en otra accion
    .map((user: User) => this.store.dispatch(new SaveUserAction(user))) // esto tambien, solo sirve para actualizar la store
    // posible nombre RefreshUserAction
    .debug("Ruta creada")
    .map(_ => go('/home'))
    .debug("Redireccion a /home");

  /* Efecto que comparte una ruta de viaje:
   * Hace la llamada http pertinente
   * Actualiza los datos de la store del usuario.
  */
  @Effect() shareRV$: Observable<Action> = this.actions$
    .ofType(RV_SHARE_ACTION)
    .switchMap((action: ShareRVAction) => this.rvService.share(action.payload))
    .debug("Se compartio la ruta de viaje: ")
    .switchMap(_ => this.userService.get()) // actualizo usuario actual en la store
    .map((user: User) => this.store.dispatch(new SaveUserAction(user)))
    .map(_ => go('/home'));

  /* Efecto que marca una ruta de viaje como favorita:
   * Hace la llamada http pertinente
   * Actualiza los datos de la store del usuario.
   */
  @Effect() favRV$: Observable<Action> = this.actions$
    .ofType(RV_FAV_ACTION)
    .switchMap((action: FavRVAction) => this.rvService.fav(action.payload))
    .debug("Se marco la ruta de viaje como favorita: ")
    .switchMap(_ => this.userService.get()) // actualizo usuario actual en la store
    .map((user: User) => this.store.dispatch(new SaveUserAction(user)))
    .map(_ => go('/home'));
}


/* funcion auxiliar que une los datos del formulario de
 * creacion de rv y los puntos del mapa.
 * Devuelve un objeto con lo necesario para crear una ruta de vida
 */
export function mkRV(rvData: {rvForm: RVFormVM, sitios: Point[], authData: AuthState}): RVDataVM {
  return {
    nombre: rvData.rvForm.nombre,
    creador: parseInt(rvData.authData.id),
    descripcion: rvData.rvForm.descripcion,
    publico: rvData.rvForm.publico,
    sitios: rvData.sitios
  }
}
