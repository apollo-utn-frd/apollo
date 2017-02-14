
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Action} from "@ngrx/store";
import {Comentario} from '../../models/comentario'
import {PostService} from "../../services/posts.service";
import {
  UPDATE_POSTS_ACTION, UpdateStoredPostsAction,
  LOAD_COMMENT_ACTION, LoadCommentAction, SaveCommentsAction
} from "../actions/ui.action";
import {Observable} from "rxjs";
import {Post} from "../../models/post";
import {RVService} from "../../services/rv.service";
import {go} from "@ngrx/router-store";

@Injectable()
export class UIEffectService {

  constructor( private actions$: Actions
    , private postService: PostService
    , private rvService: RVService
  ) { }

  @Effect() updatePosts$: Observable<Action> = this.actions$
    .ofType(UPDATE_POSTS_ACTION)
    .mergeMap(_ => this.postService.get())
    .map((posts: Post[]) => new UpdateStoredPostsAction(posts))
    .debug("publicaciones actualizadas")


  @Effect() loadComment$: Observable<Action> = this.actions$
    .ofType(LOAD_COMMENT_ACTION)
    .mergeMap((action: LoadCommentAction) => this.rvService.getCommentByID(action.payload))
    .debug("comentario cargado")
    .map((comment: Comentario) => new SaveCommentsAction(comment))
}
