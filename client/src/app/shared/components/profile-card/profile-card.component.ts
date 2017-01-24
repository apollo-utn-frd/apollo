import { Component, Input, AfterViewInit } from '@angular/core';
import { User } from '../../models/index';
import { UserService } from '../../services/index';
import { Observable } from 'rxjs/Observable';

declare var $: any;

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})

export class ProfileCardComponent implements AfterViewInit {
  user: Observable<User>;
  @Input() panelRutas: boolean = true;
  @Input() seguir: boolean = true;

  constructor(private userService: UserService) {
    this.user = this.userService.get();
  }

  ngAfterViewInit() {
    $('.seguir .btn-material').click(function() {
      var btnText = $(this).hasClass('btn-primary') ? 'Eliminar' : 'Seguir';

      $(this).toggleClass('btn-primary').toggleClass('btn-danger');
      $(this).find('.fa').toggleClass('fa-user-plus').toggleClass('fa-user-times');
      $(this).find('.text').text(btnText);
    });
  }
}
