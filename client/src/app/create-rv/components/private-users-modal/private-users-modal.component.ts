import {Component} from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-private-users-modal',
  templateUrl: 'private-users-modal.component.html',
  styleUrls: ['./private-users-modal.component.css']
})
export class PrivateUsersModalComponent {
  uncheckUsers(event: any) {
    $(event.target)
      .closest('.modal-users')
      .find('.checkbox-user.checked')
      .removeClass('checked');
  }
}
