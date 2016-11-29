import '@ngrx/core/add/operator/select';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/let';
import { Observable } from 'rxjs/Observable';
import { User } from '../../models/user';
import * as user from '../actions/user';

export interface State {
    id: string;
    token: string;
    user: User | null;
}

const initialState: State = {
    id: '',
    token: '',
    user: null
};

export function reducer(state = initialState, action: user.Actions): State {
    switch (action.type) {
        case user.ActionTypes.LOGIN: {
            const token = action.payload[0];
            const json = action.payload[1];
            const user: User = { // habia una forma menos fea de hacer esto, pero no recuerdo.
                idGoogle: json.id,
                password: json.password,
                enabled: json.enable,
                accountLocked: json.accountLocked,
                accountExpired: json.accountExpired,
                passwordExpired: json.passwordExpired,
                lastUpdated: json.lastUpdated,
                nombre: json.nombre,
                apellido: json.apellido,
                username: json.username,
                email: json.email,
                pictureUrl: json.pictureUrl,
                seguidos: json.seguidos,
                seguidores: json.seguidores,
                rutasViaje: json.rutasViaje,
                comentarios: json.comentarios,
                favoritos: json.favoritos,
                compartidos: json.compartidos,
                autorizaciones: json.autorizaciones,
                dateCreated: json.dateCreated,
                descripcion: json.descripcion,
                firstLogin: json.firstLogin
            };
            const id = user.idGoogle;
            const newState = {
                id: id,
                token: token,
                user: user
            };
            return newState;
        }

        case user.ActionTypes.LOGOUT: {
            break;
        }

        default: {
            return state;
        }
    }
}

export function getUser(state$: Observable<State>) {
    return state$.select(s => s.user);
}

export function getToken(state$: Observable<State>) {
    return state$.select(s => s.token);
}