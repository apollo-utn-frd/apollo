
import {Post} from "../../models/post";
import {Action} from "@ngrx/store";

export const UPDATE_POSTS_ACTION = 'UPDATE_POSTS_ACTION';
export const UPDATE_STORED_POSTS_ACTION = 'UPDATE_STORED_POSTS_ACTION';

export class UpdatePostsAction implements Action {
  readonly type = UPDATE_POSTS_ACTION;
  constructor(public payload?: any) {}
}

export class UpdateStoredPostsAction implements Action {
  readonly type = UPDATE_STORED_POSTS_ACTION;
  constructor(public payload?: Post[]) {}
}
