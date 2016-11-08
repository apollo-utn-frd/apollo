import { Component, Input } from '@angular/core';

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  @Input() panelRutas: boolean = true;
}
