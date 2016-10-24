import { Component } from '@angular/core';
import { NavBarComponent } from '../shared/components/index';
import { CreationFormComponent, CreationPanelComponent } from './components/index';

@Component({
  selector: 'create-rv',
  templateUrl: 'create-rv.component.html',
  styleUrls: ['./create-rv.component.css'],
  providers: [NavBarComponent, CreationFormComponent, CreationPanelComponent]
})
export class CreateRVComponent {
  constructor() {}
}
