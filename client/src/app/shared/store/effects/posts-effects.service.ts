
import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Store, Action} from "@ngrx/store";
import {ApplicationState} from "../state/application.state";
import {PostService} from "../../services/posts-service/posts.service";
import {UPDATE_POSTS_ACTION, UpdatePostsAction, UpdateStoredPostsAction } from "../actions/post.action";
import {Observable} from "rxjs";
import {Post} from "../../models/post";

@Injectable()
export class PostEffectService {

  constructor(private actions$: Actions, private store: Store<ApplicationState>, private postService: PostService) { }

  @Effect()
  updatePosts$: Observable<Action> = this.actions$
    .ofType(UPDATE_POSTS_ACTION)
    .mergeMap((action: UpdatePostsAction) => {
      return this.postService.get();
    })
    .map((posts: Post[]) => new UpdateStoredPostsAction(posts))
    .debug("publicaciones actualizadas")

}
