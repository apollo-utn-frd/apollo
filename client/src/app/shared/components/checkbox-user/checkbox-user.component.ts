import { Component } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-checkbox-user',
  templateUrl: 'checkbox-user.component.html',
  styleUrls: ['./checkbox-user.component.css']
})

export class CheckboxUserComponent {
  checkUser(event: any) {
    let target = $(event.target);

    if (!target.hasClass('checkbox-user')) {
      target = target.closest('.checkbox-user');
    }

    target.toggleClass('checked');
  }
}
