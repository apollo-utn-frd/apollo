import { Component, Input } from '@angular/core';
import { User } from '../../models/user';

declare var $: any;

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})

export class ProfileCardComponent {
  @Input() user: User;
  @Input() panelRutas: boolean = true;
  @Input() seguir: boolean = true;

  toggleButton(event: any) {
    let target = $(event.target);

    if (!target.is('button')) {
      target = target.closest('button');
    }

    let btnText = target.hasClass('btn-primary') ? 'Dejar de seguir' : 'Seguir';

    target.toggleClass('btn-primary').toggleClass('btn-danger');
    target.find('.fa').toggleClass('fa-user-plus').toggleClass('fa-user-times');
    target.find('.text').text(btnText);
  }
}
