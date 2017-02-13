import {Component, Input} from '@angular/core';
import {Post} from "../../models/post";
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {RV} from "../../models/rv";
import {ShareRVAction, FavRVAction, NewCommentAction} from "../../store/actions/rv.actions";
import {User} from "../../models/user";
import {FormGroup, FormControl} from "@angular/forms";

declare var $: any;

@Component({
  selector: 'apollo-view-rv',
  templateUrl: 'view-rv.component.html',
  styleUrls: ['./view-rv.component.css']
})
export class ViewRVComponent {

  /* Formulario para enviar comentario */
  form: FormGroup;

  /* Post que contiene la RV */
  @Input() post: Post;

  /* Usuario actualmente logueado */
  user: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.user = user);

    this.form = new FormGroup({
      contenido: new FormControl('')
    });
  }

  /* Funcion que chequea si la ruta que se pasa por parametro fue creada por el usuario actual
   * En caso afirmativo, devuelve un objeto de configuracion para setear en disabled un componente
   * */
  esRutaPropia(rv: RV): Object {
    return (this.user.id === rv.creador.id) ? {'disabled': true} : {'disabled': false}
  }

  onSubmit(event: any, rv: RV) {
    let contents = this.form.getRawValue();
    console.log("datos del comentario: ", contents);
    this.addComment(event);
    this.store.dispatch(new NewCommentAction({contenido: contents, rv: rv}));
  }

  toggleButton(event: any) {
    let target = $(event.target);

    if (!target.is('button')) {
      target = target.closest('button');
    }

    target.toggleClass('activo');
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
    $(event.target)
      .closest('apollo-view-rv')
      .find('.input-comment')[0]
      .focus();
  }

  addComment(event: any) {
    let target = $(event.target);

    if (!target.is('button')) {
      target = target.closest('button');
    }

    target.prop('disabled', true);

    let compartirSize = target
      .closest('.view-rv')
      .find('.comentar .cantidad');

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
