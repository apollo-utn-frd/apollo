
import {Action} from "@ngrx/store";
import {USER_SAVE_ACTION, SaveUserAction} from "../actions/user.actions";
import {StoreData} from "../state/storeData.state";

export function storeData(state: StoreData , action: Action): StoreData {

  switch (action.type) {
    case USER_SAVE_ACTION: {
      return handleSaveUserAction(action);
    }

    default:
      return state;
  }
}

function handleSaveUserAction(action: SaveUserAction): StoreData {
  return { currentUser: action.payload };
}
