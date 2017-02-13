import {Component, Input} from '@angular/core';
import {Post} from "../../models/post";
import {RV} from "../../models/rv";
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {ShareRVAction} from "../../store/actions/rv.actions";
import {User} from "../../models/user";

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

  user: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.user = user);
  }

  /* Funcion que chequea si la ruta que se pasa por parametro fue creada por el usuario actual
   * En caso afirmativo, devuelve un objeto de configuracion para setear en disabled un componente
   * */
  esRutaPropia(rv: RV): Object {
    return (this.user.id === rv.creador.id) ? {'disabled': true} : {'disabled': false}
  }

  showViewRV(event: any) {
    $(event.target)
      .closest('apollo-preview-rv')
      .find('apollo-view-rv .modal')
      .modal('show')
      .find('.input-comment')[0]
      .focus();

      //Codigo para eliminar los modales por defecto de Bootstrap
      $.fn.modal.Constructor.prototype.adjustDialog = function () {};
      $.fn.modal.Constructor.prototype.setScrollbar = function () {
        this.originalBodyPad = document.body.style.paddingRight || ''
        }
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
}
