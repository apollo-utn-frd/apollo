
import {Action} from "@ngrx/store";
import {USER_SAVE_ACTION, SaveUserAction, USER_EDIT_ACTION} from "../actions/user.actions";
import {StoreData} from "../state/storeData.state";
import {RV_NEW_ACTION} from "../actions/rv.actions";

export function storeData(state: StoreData , action: Action): StoreData {

  switch (action.type) {
    case USER_SAVE_ACTION: {
      return handleSaveUserAction(state, action);
    }

    case USER_EDIT_ACTION: {
      return action.payload;
    }

    case RV_NEW_ACTION: {
      return state
    }

    default:
      return state;
  }
}


function handleSaveUserAction(state: StoreData, action: SaveUserAction): StoreData {
  return {
    currentUser: action.payload
  }
}
