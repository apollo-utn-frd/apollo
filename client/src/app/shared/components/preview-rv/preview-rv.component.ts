import {Component, Input} from '@angular/core';
import {Post} from "../../models/post";
import {RV} from "../../models/rv";
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {ShareRVAction, FavRVAction} from "../../store/actions/rv.actions";
import {User} from "../../models/user";
import {go} from "@ngrx/router-store";
import {UserMinVM} from "../../models/userMin.vm";

declare var $: any;

@Component({
  selector: 'apollo-preview-rv',
  templateUrl: 'preview-rv.component.html',
  styleUrls: ['./preview-rv.component.css']
})
export class PreviewRVComponent {
  @Input() publica: boolean = true;
  @Input() compartida: boolean = false;

  @Input() post: Post;

  currentUser: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.currentUser = user);
  }

  nombresCompartidos(): string {
    return this.post.compartidos.map(c => c.nombre).join(', ');
  }

  fueCompartida(): boolean {
    return this.post.compartidos.length > 0;
  }

  isCurrentUser(user: User): boolean {
    return user.id === this.currentUser.id;
  }

  showViewRV(event: any) {
    $(event.target)
      .closest('apollo-preview-rv')
      .find('apollo-view-rv .modal')
      .modal('show')
      .find('.input-comment')
      .first()
      .focus();

    // Codigo para eliminar los modales por defecto de Bootstrap
    $.fn.modal.Constructor.prototype.adjustDialog = function () {}
    $.fn.modal.Constructor.prototype.setScrollbar = function () {
      this.originalBodyPad = document.body.style.paddingRight || '';
    }
  }

  router(event: any) {
    event.preventDefault();
    this.store.dispatch(go(event.currentTarget.getAttribute('href')));
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
}
