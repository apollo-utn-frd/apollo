import {Component, OnInit, NgZone, ElementRef, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MapsAPILoader} from "angular2-google-maps/core";

@Component({
  selector: 'apollo-search-location-textbox',
  templateUrl: 'search-location-textbox.component.html',
  styleUrls: ['./search-location-textbox.component.css']
})
export class SearchLocationTextBox implements OnInit {
  public latitude: number = -34.1785255; // lat de la facu por defecto
  public longitude: number = -58.9616511; // long de la facu por defecto
  public zoom: number = 16;

  public searchControl: FormControl;

  @ViewChild("search")
  public searchElementRef: ElementRef;

  constructor(private mapsAPILoader: MapsAPILoader, private ngZone: NgZone) {}

  ngOnInit() {
    //create search FormControl
    this.searchControl = new FormControl();

    //load Places Autocomplete
    this.mapsAPILoader.load().then(() => {
      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ["address"]
      });
      autocomplete.addListener("place_changed", () => {
        this.ngZone.run(() => {
          //get the place result
          let place: google.maps.places.PlaceResult = autocomplete.getPlace();

          //verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          //set latitude, longitude and zoom
          this.latitude = place.geometry.location.lat();
          this.longitude = place.geometry.location.lng();
          this.zoom = 12;
        });
      });
    });
  }

}
