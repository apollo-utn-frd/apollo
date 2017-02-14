
import {Post} from "../../models/post";
import {Comentario} from "../../models/comentario";

export interface UIState {
  posts: Post[]
  commentsFromCurrentRV: Comentario[]
}

export const INITIAL_UI_STATE: UIState = {
  posts: [],
  commentsFromCurrentRV: []
};
