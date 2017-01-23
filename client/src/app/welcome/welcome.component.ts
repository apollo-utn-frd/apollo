import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared/services/index';
import { Store } from '@ngrx/store';
import { State, getUser } from '../shared/store/index';
import { Observable } from 'rxjs/Observable';
import { User } from '../shared/models/index';
import { Router } from '@angular/router';

@Component({
  selector: 'apollo-welcome',
  templateUrl: 'welcome.component.html',
  styleUrls: ['welcome.component.css'],
})
export class WelcomeComponent implements OnInit {
  user$: Observable<User>;

  constructor( private auth: AuthService
             , private store: Store<State>
             , private router: Router
             ) {
    this.user$ = store.let(getUser);
   }

  ngOnInit() {
    this.auth.login();
    this.user$.subscribe(
      (u: User) => {
        if (u !== null) {
          if (u.firstLogin) {
            this.router.navigateByUrl('/login');
          } else {
            this.router.navigateByUrl('/home');
          }
        }
      },
      err => console.error(err)
    );
  }

}
