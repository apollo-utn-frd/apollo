import { Component, Input, AfterViewInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-notification-item',
  templateUrl: 'notification-item.component.html',
  styleUrls: ['./notification-item.component.css']
})
export class NotificationItemComponent implements AfterViewInit {
  /* Esto hay que eliminarlo cuando se le agregue funcionalidad, por ahora es de muestra. */
  @Input() seguido: boolean = false;
  @Input() favorito: boolean = false;
  @Input() comentado: boolean = false;
  @Input() compartido: boolean = false;

  ngAfterViewInit() {
    $('.details[hidden]').remove(); // GG Angular
  }
}
