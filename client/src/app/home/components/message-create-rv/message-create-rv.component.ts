import { Component } from '@angular/core';
import {ApplicationState} from "../../../shared/store/state/application.state";
import {Store} from "@ngrx/store";
import {go} from "@ngrx/router-store";

@Component({
    selector: 'apollo-message-create-rv',
    templateUrl: 'message-create-rv.component.html',
    styleUrls: ['./message-create-rv.component.css']
})
export class MessageCreateRVComponent {
  constructor(private store: Store<ApplicationState>) {}

  goToCreateRV() {
    this.store.dispatch(go('new/rv'));
  }
}
