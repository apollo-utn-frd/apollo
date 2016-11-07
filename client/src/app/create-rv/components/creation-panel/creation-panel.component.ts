import { Component } from '@angular/core';
import { SebmGoogleMap, SebmGoogleMapMarker, MouseEvent } from 'angular2-google-maps/core';
import {GoogleplaceDirective} from 'angular2-google-map-auto-complete/directives/googleplace.directive';

@Component({
  selector: 'apollo-creation-panel',
  templateUrl: 'creation-panel.component.html',
  styleUrls: ['./creation-panel.component.css']
})
export class CreationPanelComponent {
  lat: number = -34.1785255;
  lng: number = -58.9616511;
  zoom = 16;
  ui: boolean = false;
  markers: Marker[];
  lines: Line[];
  pointA: Point;
  pointB: Point;
  color: string = '#000';

  constructor() {
    this.markers = [];
    this.lines = [];
  }

  onMapClick($event: MouseEvent) {
    if (this.markers.length !== 0) {
      let len = this.markers.length - 1;
      let lastElem: Marker = this.markers[len];
      this.pointA = {
        lat: lastElem.lat,
        lng: lastElem.lng
      };
    } else {
      this.pointA = {
        lat: $event.coords.lat,
        lng: $event.coords.lng
      };
    }

    this.markers.push({
      lat: $event.coords.lat,
      lng: $event.coords.lng
    });

    this.pointB = {
      lat: $event.coords.lat,
      lng: $event.coords.lng
    };

    this.lines.push({
      pointA: this.pointA,
      pointB: this.pointB
    });
  }
}

interface Marker {
  lat: number;
  lng: number;
  label?: string;
  draggable?: boolean;
}

interface Point {
  lat: number;
  lng: number;
}

interface Line {
  pointA: Point;
  pointB: Point;
}
