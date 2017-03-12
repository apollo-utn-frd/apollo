import { Component, Input } from '@angular/core';
import { User } from '../../models/user';

@Component({
  selector: 'apollo-preview-user',
  templateUrl: 'preview-user.component.html',
  styleUrls: ['./preview-user.component.css']
})

export class PreviewUserComponent {
  @Input() user: User;
}
