import { Component } from '@angular/core';

declare var $: any;

@Component({
    selector: 'apollo-search',
    templateUrl: 'search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent {
  changeTab(event: any) {
    event.preventDefault();
    $(event.currentTarget).tab('show');
  }
}
