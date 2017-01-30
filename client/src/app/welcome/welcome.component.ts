import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs/Observable';
import {LoginAction} from "../shared/store/actions/auth.actions";
import {go} from "@ngrx/router-store";
import {AuthState} from "../shared/store/state/auth.state";
import {ApplicationState} from "../shared/store/state/application.state";

@Component({
  selector: 'apollo-welcome',
  templateUrl: 'welcome.component.html',
  styleUrls: ['welcome.component.css'],
})
export class WelcomeComponent implements OnInit {
  credentials: AuthState;

  constructor(private store: Store<ApplicationState>) {
    if (window.localStorage.length > 0) {
      this.credentials = {
        id: window.localStorage.getItem('id'),
        token: window.localStorage.getItem('token')
      }
    } else if (window.location.hash.substr(1)) {
      this.credentials = {
        id: window.localStorage.getItem('id'),
        token: window.location.hash.substr(1)
      }
    }
  }

  ngOnInit() {
    if (this.credentials) {
      this.store.dispatch(new LoginAction(this.credentials));
      //this.store.dispatch(go(['/login'])); // fijarse si se puede mejorar
    }
  }
}
