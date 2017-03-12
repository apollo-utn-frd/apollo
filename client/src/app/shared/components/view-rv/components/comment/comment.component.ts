import {Component, Input} from '@angular/core';
import {Comentario} from "../../../../models/comentario";
import {ApplicationState} from "../../../../store/state/application.state";
import {Store} from "@ngrx/store";
import {go} from "@ngrx/router-store";

@Component({
  selector: 'apollo-comment',
  templateUrl: 'comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comentario;

  constructor(private store: Store<ApplicationState>) {}

  router(event: any) {
    event.preventDefault();
    this.store.dispatch(go(event.currentTarget.getAttribute('href')));
  }
}
