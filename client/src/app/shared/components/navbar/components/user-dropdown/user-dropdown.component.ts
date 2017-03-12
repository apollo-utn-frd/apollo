import { Component } from '@angular/core';
import {ApplicationState} from "../../../../store/state/application.state";
import {Store} from "@ngrx/store";
import {User} from "../../../../models/user";

@Component({
  selector: 'apollo-user-dropdown',
  templateUrl: 'user-dropdown.component.html',
  styleUrls: ['./user-dropdown.component.css']
})
export class UserDropdownComponent {
  user: User;

  constructor(private store: Store<ApplicationState>) {
    this.store.select(state => state.storeData.currentUser)
      .subscribe((user: User) => this.user = user);
  }
}
