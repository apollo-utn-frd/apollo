import {Component, Input, OnInit, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {Post} from "../../models/post";
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {RV} from "../../models/rv";
import {ShareRVAction, FavRVAction, NewCommentAction} from "../../store/actions/rv.actions";
import {User} from "../../models/user";
import {FormGroup, FormControl} from "@angular/forms";
import {Comentario} from "../../models/comentario";
import {Observable} from "rxjs";
import {LoadCommentAction} from "../../store/actions/ui.action";
import {go} from "@ngrx/router-store";

declare var $: any;

@Component({
  selector: 'apollo-view-rv',
  templateUrl: 'view-rv.component.html',
  styleUrls: ['./view-rv.component.css']
})
export class ViewRVComponent implements OnInit {
  /* Post que contiene la RV */
  @Input() post: Post;

  /* Usuario actualmente logueado */
  currentUser: User;
  comentarios: Comentario[];

  /* Formulario para enviar comentario */
  form: FormGroup;

  constructor(private ref: ChangeDetectorRef, private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.currentUser = user);
    this.store.select(state => state.uiState.commentsFromCurrentRV)
      .subscribe((cs: Comentario[]) => this.comentarios = cs.filter(c => c.rutaViaje.id === this.post.rutaViaje.id));
    this.form = new FormGroup({
      contenido: new FormControl('')
    });
  }

  ngOnInit() {
    this.post.rutaViaje.comentarios
      .map((objetoID: {id: number}) => objetoID.id)
      .forEach((id: number) => this.store.dispatch(new LoadCommentAction(id)));
  }

  isCurrentUser(user: User): boolean {
    return user.id === this.currentUser.id;
  }

  onSubmit(event: any, rv: RV) {
    let contents = this.form.getRawValue();
    console.log("datos del comentario: ", contents);
    this.addComment(event);
    this.store.dispatch(new NewCommentAction({contenido: contents, rv: rv}));
    this.ref.detectChanges();
  }

  openView(post: Post) { // cambiar evento bajo el qeu se activa?
    post.rutaViaje.comentarios
      .map((objetoID: {id: number}) => objetoID.id)
      .forEach((id: number) => this.store.dispatch(new LoadCommentAction(id)));
  }

  toggleButton(event: any) {
    $(event.currentTarget).toggleClass('activo');
  }

  shareButton(event: any, rv: RV) {
    this.toggleButton(event);
    this.store.dispatch(new ShareRVAction(rv));
  }

  favButton(event: any, rv: RV) {
    this.toggleButton(event);
    this.store.dispatch(new FavRVAction(rv));
  }

  focusComment(event: any) {
    $(event.currentTarget)
      .closest('apollo-view-rv')
      .find('.new-comment .input')
      .first()
      .focus();
  }

  addComment(event: any) {
    let target = $(event.currentTarget);

    target.prop('disabled', true);

    let compartirSize = target
      .closest('.view-rv')
      .find('.comentarios .cantidad');

    compartirSize.text(parseInt(compartirSize.text(), 10) + 1);

    let listComments = target
      .closest('.comments')
      .find('.list-comments');

    let newComment = listComments
      .find('.comment')
      .first()
      .clone()
      .hide();

    let hr = listComments
      .find('.line')
      .first()
      .clone();

    listComments.prepend(hr);
    listComments.prepend(newComment);

    newComment.slideDown('slow', function() {
      target.prop('disabled', false);
    });
  }
}
