import { Component, AfterViewInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-notifications',
  templateUrl: 'notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements AfterViewInit {
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
