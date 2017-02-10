
import {Action} from "@ngrx/store";
import {UIState} from "../state/ui.state";
import {UPDATE_STORED_POSTS_ACTION, UpdateStoredPostsAction} from "../actions/post.action";

export function uiState(state: UIState , action: Action): UIState {

  switch (action.type) {
    case UPDATE_STORED_POSTS_ACTION: {
      return handleUpdateStoredPostsAction(state, action);
    }

    default:
      return state;
  }
}

function handleUpdateStoredPostsAction(state: UIState, action: UpdateStoredPostsAction): UIState {
  return {
    posts: action.payload
  }
}
