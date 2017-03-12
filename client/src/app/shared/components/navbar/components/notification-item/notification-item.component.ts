import { Component, Input } from '@angular/core';

@Component({
  selector: 'apollo-notification-item',
  templateUrl: 'notification-item.component.html',
  styleUrls: ['./notification-item.component.css']
})
export class NotificationItemComponent {
  /* Esto hay que eliminarlo cuando se le agregue funcionalidad, por ahora es de muestra. */
  @Input() seguido: boolean = false;
  @Input() favorito: boolean = false;
  @Input() comentado: boolean = false;
  @Input() compartido: boolean = false;
}
