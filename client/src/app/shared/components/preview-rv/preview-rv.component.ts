import { Component, Input, AfterViewInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'apollo-preview-rv',
  templateUrl: 'preview-rv.component.html',
  styleUrls: ['./preview-rv.component.css']
})
export class PreviewRVComponent implements AfterViewInit {
  @Input() publica: boolean = true;
  @Input() compartida: boolean = false;

  ngAfterViewInit() {
    $('.actions button').click(function() {
      $(this).toggleClass('activo');
    });
  }
}
