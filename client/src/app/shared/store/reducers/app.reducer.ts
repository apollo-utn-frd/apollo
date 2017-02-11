
import {authState} from "./auth.reducer";
import {storeData} from "./storeData.reducer";
import {routerReducer} from "@ngrx/router-store";
import {uiState} from "./ui.reducer";

// aca van los reducers de la app
export const appReducers = {
  authState,
  uiState,
  storeData,
  router: routerReducer
};
