import { Component, Input, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {User} from "../../models/user";

declare var $: any;

@Component({
  selector: 'apollo-profile-card',
  templateUrl: 'profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})

export class ProfileCardComponent implements AfterViewInit {
  @Input() user: Observable<User>;
  @Input() panelRutas: boolean = true;
  @Input() seguir: boolean = true;

  constructor() { }

  ngAfterViewInit() {
    $('.seguir .btn-material').click(function() {
      var btnText = $(this).hasClass('btn-primary') ? 'Eliminar' : 'Seguir';

      $(this).toggleClass('btn-primary').toggleClass('btn-danger');
      $(this).find('.fa').toggleClass('fa-user-plus').toggleClass('fa-user-times');
      $(this).find('.text').text(btnText);
    });
  }
}
