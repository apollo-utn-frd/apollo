import { Component } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-checkbox-user',
  templateUrl: 'checkbox-user.component.html',
  styleUrls: ['./checkbox-user.component.css']
})

export class CheckboxUserComponent {
  checked: boolean = false;

  checkUser(event: any) {
    this.checked = !this.checked;
  }

  classCheckbox() {
    return {checkbox: true, shadow: true, checked: this.checked};
  }
}
