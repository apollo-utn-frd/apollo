import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../models/index';
import { Response } from '@angular/http';
import { UserService } from '../../services/index';

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  user: Object;
  @Input() panelRutas: boolean = true;

  constructor(private userService: UserService) {
    this.user = this.userService.get();
  }
}
