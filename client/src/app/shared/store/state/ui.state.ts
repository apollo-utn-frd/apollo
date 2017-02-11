
import {Post} from "../../models/post";

export interface UIState {
  posts: Post[]
}

export const INITIAL_UI_STATE: UIState = {
  posts: []
};
