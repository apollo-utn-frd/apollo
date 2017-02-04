import { Component } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-view-rv',
  templateUrl: 'view-rv.component.html',
  styleUrls: ['./view-rv.component.css']
})
export class ViewRVComponent {
  toggleButton(event: any) {
    let target = $(event.target);

    if (!target.is('button')) {
      target = target.closest('button');
    }

    target.toggleClass('activo');
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
