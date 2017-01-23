import { Component, Output, EventEmitter } from '@angular/core';
import { SebmGoogleMap, SebmGoogleMapMarker, MouseEvent } from 'angular2-google-maps/core';

@Component({
  selector: 'apollo-creation-panel',
  templateUrl: 'creation-panel.component.html',
  styleUrls: ['./creation-panel.component.css']
})
export class CreationPanelComponent {
  // Datos para setear el mapa
  lat: number = -34.1785255; // latitud de la facu
  lng: number = -58.9616511; // longitud de la facu
  zoom = 16;
  ui: boolean = false;

  // datos sobre los marcadores puestos en el mapa
  markers: Marker[];
  lines: Line[];
  pointA: Point;
  pointB: Point;
  color: string = '#000';

  // marcadores que se envian al padre cuando el padre se los pide
  @Output() marcadores: EventEmitter<Point> = new EventEmitter<Point>();

  constructor() {
    this.markers = [];
    this.lines = [];
  }

  onMapRightClick($event: MouseEvent): void {
    if (this.markers.length !== 0) {
      this.markers.pop();
      this.lines.pop();
    }
  }

  onMapClick($event: MouseEvent): void {
    if (this.markers.length !== 0) {
      let len = this.markers.length - 1;
      let lastElem: Marker = this.markers[len];
      this.pointA = {
        latitud: lastElem.lat,
        longitud: lastElem.lng
      };
    } else {
      this.pointA = {
        latitud: $event.coords.lat,
        longitud: $event.coords.lng
      };
    }

    this.markers.push({
      lat: $event.coords.lat,
      lng: $event.coords.lng
    });

    this.pointB = {
      latitud: $event.coords.lat,
      longitud: $event.coords.lng
    };

    this.lines.push({
      pointA: this.pointA,
      pointB: this.pointB
    });
  }

}



// clases del dominio para los mapas, pasar a otro archivo.

interface Marker {
  lat: number;
  lng: number;
  label?: string;
  draggable?: boolean;
}

interface Point {
  latitud: number;
  longitud: number;
}

interface Line {
  pointA: Point;
  pointB: Point;
}
