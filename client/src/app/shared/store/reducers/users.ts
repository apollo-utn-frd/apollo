import '@ngrx/core/add/operator/select';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/let';
import { Observable } from 'rxjs/Observable';
import { User } from '../../models/user';
import * as user from '../actions/user';

export interface State {
    id: number;
    token: string;
    user: User | null;
}

const initialState: State = {
    id: 0,
    token: '',
    user: null
};

export function reducer(state = initialState, action: user.Actions): State {
    switch (action.type) {
        case user.ActionTypes.LOGIN: {
            const token = action.payload[0];
            const json = action.payload[1];
            const user: User = { // habia una forma menos fea de hacer esto, pero no recuerdo.
                id: json.id,
                firstname: json.nombre,
                lastname: json.apellido,
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
                description: json.descripcion,
                firstLogin: json.firstLogin
            };
            const id = user.id;
            const newState = {
                id: id,
                token: token,
                user: user
            };
            console.log(newState);

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
