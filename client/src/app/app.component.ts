import { Component } from '@angular/core';
import {ApplicationState} from "./shared/store/state/application.state";
import {Store} from "@ngrx/store";
import {init} from "protractor/built/launcher";

@Component({
  selector: 'apollo-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private store: Store<ApplicationState>) {
   // this.store.dispatch(new LoadAppAction())
  }
}
