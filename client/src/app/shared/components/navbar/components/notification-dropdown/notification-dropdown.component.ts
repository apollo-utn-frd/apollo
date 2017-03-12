import { Component, AfterViewInit } from '@angular/core';
import {ApplicationState} from "../../../../store/state/application.state";
import {Store} from "@ngrx/store";

declare var $: any;

@Component({
  selector: 'apollo-notification-dropdown',
  templateUrl: 'notification-dropdown.component.html',
  styleUrls: ['./notification-dropdown.component.css']
})
export class NotificationDropdownComponent implements AfterViewInit {
  constructor(private store: Store<ApplicationState>) {}

  ngAfterViewInit() {
    $('.notifications-dropdown').on('hidden.bs.dropdown', function() {
      $('.notifications-count').hide();
      $('.notifications-count count').text('0');
      $(this).find('.item').addClass('read');
    });

    /* Elimino el borde del último item. */
    $('.item-border').last().css('border-bottom', 'none');
  }
}
