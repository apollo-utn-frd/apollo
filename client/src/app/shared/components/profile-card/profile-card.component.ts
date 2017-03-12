import { Component, Input } from '@angular/core';
import { User } from '../../models/user';
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";

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

  currentUser: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.currentUser = user);
  }

  toggleButton(event: any) {
    let target = $(event.currentTarget);

    let btnText = target.hasClass('btn-primary') ? 'Dejar de seguir' : 'Seguir';

    target.toggleClass('btn-primary').toggleClass('btn-danger');
    target.find('.fa').toggleClass('fa-user-plus').toggleClass('fa-user-times');
    target.find('.text').text(btnText);
  }

  isCurrentUser(user: User): boolean {
    return user.id === this.currentUser.id;
  }
}
