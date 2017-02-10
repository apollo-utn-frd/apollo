import { Component, Input } from '@angular/core';
import {ApplicationState} from "../../store/state/application.state";
import {Store} from "@ngrx/store";
import {LogoutAction} from "../../store/actions/auth.actions";
import {go} from "@ngrx/router-store";

@Component({
  selector: 'apollo-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavBarComponent {
  @Input() search: boolean = true;
  @Input() dropdown: boolean = true;

  constructor(private store: Store<ApplicationState>) {}

  logout() {
    this.store.dispatch(new LogoutAction());
    this.store.dispatch(go('/welcome'));
  }
}
