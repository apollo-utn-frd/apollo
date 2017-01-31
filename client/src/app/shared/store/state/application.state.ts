
import {RouterState} from "@ngrx/router-store";
import {AuthState, INITIAL_AUTH_STATE} from "./auth.state";
import {UIState, INITIAL_UI_STATE} from "./ui.state";
import {StoreData, INITIAL_STORE_DATA} from "./storeData.state";

export interface ApplicationState {
  authState: AuthState,
  uiState: UIState,
  storeData: StoreData,
  router: RouterState
}

export const INITIAL_APP_STATE: ApplicationState = {
  authState: INITIAL_AUTH_STATE,
  uiState: INITIAL_UI_STATE,
  storeData: INITIAL_STORE_DATA,
  router: {
    path: '/'
  }
};
