import { Component } from '@angular/core';
import {MouseEvent} from 'angular2-google-maps/core';
import {Marker} from "../models/Marker";
import {Point} from "../models/Point";
import {Line} from "../models/Line";

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

  getMarkers(): Marker[] {
    return this.markers;
  }

  // Agrega un marcador al mapa y crea una linea que lo une.
  // este metodo capaz se puede hacer de forma mas facil.
  // la verdad es que lo hice asi nomas (?)
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



