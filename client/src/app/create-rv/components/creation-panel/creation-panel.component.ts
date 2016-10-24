import { Component } from '@angular/core';
import {SebmGoogleMap, SebmGoogleMapMarker, MouseEvent } from 'angular2-google-maps/core';

@Component({
  selector: 'creation-panel',
  templateUrl: 'creation-panel.component.html',
  styleUrls: ['./creation-panel.component.css']
})
export class CreationPanelComponent {
  lat: number = 51.678418;
  lng: number = 7.809007;
  ui: boolean = false;
  markers: Marker[];
  lines: Line[];
  pointA: Point;
  pointB: Point;
  color: string = "#00FFFF";

  constructor() {
    this.markers = [];
    this.lines = [];
  }

  onMapClick($event: MouseEvent) {
    if (this.markers.length != 0) {
      let len = this.markers.length - 1;
      let lastElem: Marker = this.markers[len];
      this.pointA = {
        lat: lastElem.lat,
        lng: lastElem.lng
      }  
    } else {
      this.pointA = {
        lat: $event.coords.lat,
        lng: $event.coords.lng
      }
    }

    this.markers.push({
      lat: $event.coords.lat,
      lng: $event.coords.lng
    });

    this.pointB = {
      lat: $event.coords.lat,
      lng: $event.coords.lng
    }

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
