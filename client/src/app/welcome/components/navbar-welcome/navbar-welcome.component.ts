import { Component, Input } from '@angular/core';

declare var $ : any;

@Component({
  selector: 'apollo-navbar-welcome',
  templateUrl: 'navbar-welcome.component.html',
  styleUrls: ['./navbar-welcome.component.css']
})
export class NavBarWelcomeComponent {
  ngAfterViewInit() {
    // jQuery for page scrolling feature - requires jQuery Easing plugin
    $('a.page-scroll').click(function(event) {
      $('.navbar-nav .active').removeClass('active');
      $(this).parent().addClass('active');

      $('html, body').stop().animate({
        scrollTop: $($(this).attr('href')).offset().top
      }, 1500, 'easeInOutExpo');

      event.preventDefault();
    });
  }
}
