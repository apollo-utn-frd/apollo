import {Component, OnInit} from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../shared/store/state/application.state";
import {User} from "../shared/models/user";
import {Observable} from "rxjs";
import {UpdatePostsAction, LoadCommentAction} from "../shared/store/actions/ui.action";
import {Post} from "../shared/models/post";
import {PreviewRVComponent} from "../shared/components/preview-rv/preview-rv.component";

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent, ProfileCardComponent, PreviewRVComponent]
})
export class HomeComponent implements OnInit {
  users$: Observable<User>;
  posts: Post[];

  constructor(private store: Store<ApplicationState>) {
    this.users$ = this.store.select(st => st.storeData.currentUser);
    this.store.dispatch(new UpdatePostsAction());
    this.store.select((state: ApplicationState) => state.uiState.posts)
      .subscribe(posts => this.posts = posts)
  }

  ngOnInit() {
    this.store.dispatch(new UpdatePostsAction());
  }
}
