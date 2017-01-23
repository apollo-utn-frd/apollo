import { Component, Input } from '@angular/core';
import { User } from '../../models/index';
import { UserService } from '../../services/index';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  user: Observable<User>;
  @Input() panelRutas: boolean = true;
  @Input() seguir: boolean = true;

  constructor(private userService: UserService) {
    this.user = this.userService.get();
  }
}
