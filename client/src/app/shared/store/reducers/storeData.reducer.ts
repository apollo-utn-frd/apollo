
import {Action} from "@ngrx/store";
import {USER_SAVE_ACTION, SaveUserAction} from "../actions/user.actions";
import {StoreData} from "../state/storeData.state";

export function storeData(state: StoreData , action: Action): StoreData {
  switch (action.type) {
    case USER_SAVE_ACTION: {
      return handleSaveUserAction(state, action);
    }
    default:
      return state;
  }
}


function handleSaveUserAction(state: StoreData, action: SaveUserAction): StoreData {
  return {
    currentUser: action.payload
  }
};
