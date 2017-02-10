
import {SaveAuthStateAction, AUTH_LOGOUT_ACTION, AUTH_SAVE_ACTION} from "../actions/auth.actions";
import {Action} from "@ngrx/store";
import {AuthState, INITIAL_AUTH_STATE} from "../state/auth.state";

export function authState(state: AuthState , action: Action): AuthState {
  switch (action.type) {
    case AUTH_SAVE_ACTION: {
      return handleSaveAuthStateAction(state, action)
    }

    case AUTH_LOGOUT_ACTION: {
      window.localStorage.clear();
      return INITIAL_AUTH_STATE; // borro el token y el id del usuario. naive impl.
    }

    default:
      return state;
  }
}

function handleSaveAuthStateAction(_: AuthState, action: SaveAuthStateAction) {
  return {
    id: action.payload.id,
    token:action.payload.token
  };
}
