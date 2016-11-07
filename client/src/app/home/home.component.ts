import { Component } from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent, ProfileCardComponent]
})
export class HomeComponent {
  constructor() {}
}
