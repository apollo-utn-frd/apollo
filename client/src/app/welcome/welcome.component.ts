import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared/services/index';

@Component({
  selector: 'apollo-welcome',
  templateUrl: 'welcome.component.html',
  styleUrls: ['welcome.component.css'],
})
export class WelcomeComponent implements OnInit {

  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.auth.login();
  }
}

