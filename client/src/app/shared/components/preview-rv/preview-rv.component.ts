import {Component, Input} from '@angular/core';
import {Post} from "../../models/post";

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

  constructor() {}
  showViewRV(event: any) {
    $(event.target)
      .closest('apollo-preview-rv')
      .find('apollo-view-rv .modal')
      .modal('show')
      .find('.input-comment')[0]
      .focus();

      //Codigo para eliminar los modales por defecto de Bootstrap
      $.fn.modal.Constructor.prototype.adjustDialog = function () {}
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
}
