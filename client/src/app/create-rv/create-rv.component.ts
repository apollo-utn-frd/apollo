import { Component } from '@angular/core';
import { NavBarComponent } from '../shared/components/index';
import { CreationFormComponent, CreationPanelComponent } from './components/index';
import {ApplicationState} from "../shared/store/state/application.state";
import {Store} from "@ngrx/store";

@Component({
  selector: 'apollo-create-rv',
  templateUrl: 'create-rv.component.html',
  styleUrls: ['./create-rv.component.css'],
  providers: [NavBarComponent, CreationFormComponent, CreationPanelComponent]
})
export class CreateRVComponent {
  constructor(private store: Store<ApplicationState>) { }
}
