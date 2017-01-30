
import {SaveAuthStateAction, AUTH_LOGOUT_ACTION, AUTH_SAVE_ACTION} from "../actions/auth.actions";
import {Action} from "@ngrx/store";
import {AuthState, INITIAL_AUTH_STATE} from "../state/auth.state";

export function authState(state: AuthState , action: Action): AuthState {
  switch (action.type) {
    case AUTH_SAVE_ACTION: {
      return handleSaveAuthStateAction(state, action)
    }

    case AUTH_LOGOUT_ACTION: {
      return INITIAL_AUTH_STATE; // borro el token y el id del usuario. naive impl.
    }

    default:
      return state;
  }
}

function handleSaveAuthStateAction(state: AuthState, action: SaveAuthStateAction) {
  return {
    id: action.payload.id,
    token:action.payload.token
  };
}





/*
 function handleLoginAction(state: AuthState, action: LoginAction): AuthState {
 const token = action.payload[0];
 const json = action.payload[1];
 return {
 id: json.id,
 token: token,
 };
 }
 */
/*
 export function getUser(state$: Observable<State>) {
 return state$.select(s => s.user);
 }

 export function getToken(state$: Observable<State>) {
 return state$.select(s => s.token);
 }
 */
