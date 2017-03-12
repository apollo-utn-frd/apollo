import { Component, Input } from '@angular/core';
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {go} from "@ngrx/router-store";

@Component({
  selector: 'apollo-navbar-logo',
  templateUrl: 'navbar-logo.component.html',
  styleUrls: ['./navbar-logo.component.css']
})
export class NavBarLogoComponent {
  constructor(private store: Store<ApplicationState>) {}

  router(event: any) {
    event.preventDefault();
    this.store.dispatch(go(event.currentTarget.getAttribute('href')));
  }
}
