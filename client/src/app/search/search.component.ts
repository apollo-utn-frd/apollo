import { Component } from '@angular/core';

declare var $: any;

@Component({
    selector: 'apollo-search',
    templateUrl: 'search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent {
  changeTab(event: any) {
    let target = $(event.target);

    if (!target.is('a')) {
      target = target.closest('a');
    }

    target.tab('show');

    event.preventDefault();
  }
}
