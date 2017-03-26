import {Component, Input} from '@angular/core';
import {Post} from "../../models/post";
import {RV} from "../../models/rv";
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {ShareRVAction, FavRVAction} from "../../store/actions/rv.actions";
import {User} from "../../models/user";
import {UserMinVM} from "../../models/userMin.vm";

declare var $: any;

@Component({
  selector: 'apollo-preview-rv',
  templateUrl: 'preview-rv.component.html',
  styleUrls: ['./preview-rv.component.css']
})
export class PreviewRVComponent {
  @Input() post: Post;

  currentUser: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.currentUser = user);
  }

  isCurrentUser(user: User): boolean {
    return user.id === this.currentUser.id;
  }

  showViewRV(event: any) {
    $(event.target)
      .closest('apollo-preview-rv')
      .find('.modal')
      .modal('show')
      .find('.input')
      .first()
      .focus();

    // Codigo para eliminar los modales por defecto de Bootstrap
    $.fn.modal.Constructor.prototype.adjustDialog = function () {}
    $.fn.modal.Constructor.prototype.setScrollbar = function () {
      this.originalBodyPad = document.body.style.paddingRight || '';
    }
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
