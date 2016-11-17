import { Component, OnInit } from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';
import { UserService } from '../shared/services/index';
import { User } from '../shared/models/index';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import { Store } from '@ngrx/store';
import { State } from '../shared/store/index';

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent, ProfileCardComponent]
})
export class HomeComponent {
  users$: User;
  constructor( private http: Http
             , private store: Store<State>
             , private userService: UserService) {
             }

}
