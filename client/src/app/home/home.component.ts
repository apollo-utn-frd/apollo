import { Component } from '@angular/core';
import { AuthService, UserService } from '../shared/services/index';
import { NavBarComponent } from '../shared/components/index';

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent]
})
export class HomeComponent {
  constructor() {}
}