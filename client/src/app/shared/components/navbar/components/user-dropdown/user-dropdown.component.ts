import { Component } from '@angular/core';
import {ApplicationState} from "../../../../store/state/application.state";
import {Store} from "@ngrx/store";
import {go} from "@ngrx/router-store";

declare var $: any;

@Component({
  selector: 'apollo-user-dropdown',
  templateUrl: 'user-dropdown.component.html',
  styleUrls: ['./user-dropdown.component.css']
})
export class UserDropdownComponent {
  constructor(private store: Store<ApplicationState>) {}

  router(event: any) {
    event.preventDefault();
    this.store.dispatch(go(event.currentTarget.getAttribute('href')));
  }
}
