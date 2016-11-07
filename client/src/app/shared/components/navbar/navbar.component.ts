import { Component, Input } from '@angular/core';

@Component({
  selector: 'apollo-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavBarComponent {
  @Input() search: boolean = true;
  @Input() dropdown: boolean = true;
}
