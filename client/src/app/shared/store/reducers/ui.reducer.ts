
import {Action} from "@ngrx/store";
import {UIState} from "../state/ui.state";
import {
  UPDATE_STORED_POSTS_ACTION, UpdateStoredPostsAction, SaveCommentsAction,
  SAVE_COMMENTS_ACTION, CleanCommentsAction, CLEAN_COMMENTS_ACTION
} from "../actions/ui.action";
import {Comentario} from "../../models/comentario";

export function uiState(state: UIState , action: Action): UIState {

  switch (action.type) {
    case UPDATE_STORED_POSTS_ACTION: {
      return handleUpdateStoredPostsAction(state, action);
    }

    case SAVE_COMMENTS_ACTION: {
      return handleSaveCommentsAction(state, action);
    }

    case CLEAN_COMMENTS_ACTION: {
      return handleCleanCommentsAction(state);
    }

    default:
      return state;
  }
}

// handlers
function handleUpdateStoredPostsAction(state: UIState, action: UpdateStoredPostsAction): UIState {
  return {
    posts: action.payload,
    commentsFromCurrentRV: state.commentsFromCurrentRV
  }
}

function handleSaveCommentsAction(state: UIState, action: SaveCommentsAction): UIState {
  let newComments: Comentario[] = _.cloneDeep(state.commentsFromCurrentRV);
  newComments.push(action.payload);
  return {
    posts: state.posts,
    commentsFromCurrentRV: newComments
  }
}

function handleCleanCommentsAction(state: UIState): UIState {
  return {
    posts: state.posts,
    commentsFromCurrentRV: []
  }
}
