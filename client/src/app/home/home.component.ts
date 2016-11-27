import { Component, OnInit, AfterViewInit } from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';
import { UserService } from '../shared/services/index';
import { User } from '../shared/models/index';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import { Store } from '@ngrx/store';
import { State } from '../shared/store/index';

declare var $: any;

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent, ProfileCardComponent]
})
export class HomeComponent implements AfterViewInit {
  users$: User;

  ngAfterViewInit() {
    ajustarLayout();

    $('apollo-preview-rv:eq(0) img').load(function() {
      ajustarMensaje();
    });

    $(window).resize(function() {
      ajustarLayout();
    });

    $('#expand').click(function() {
      $('#perfil').toggleClass('hidden');
      $('#publicaciones').toggleClass('reduced');
      $(this).find('.fa').toggleClass('fa-chevron-left').toggleClass('fa-chevron-right');

      ajustarLayout();
    });
  }

  constructor( private http: Http
             , private store: Store<State>
             , private userService: UserService) {
             }
}

function ajustarLayout() {
  ajustarPublicaciones();
  ajustarMensaje();
}

/**
 * Ajusta la altura del mensaje a la de una preview-rv sin la leyenda de ruta de viaje compartida.
 * En caso de que no haya ninguna preview-rv se le asigna una altura de 400px.
 */
function ajustarMensaje() {
  var previewRV = $('apollo-preview-rv').first();

  var height = previewRV.height() || 400;

  var legend = previewRV.find('.legend:not(:hidden)');

  // Si la preview-rv tiene la leyenda de ruta de viaje compartida entonces resto su altura.
  if (legend.length) {
    height -= legend.outerHeight();
  }

  $('apollo-message-create-rv').css('height', height + 'px');
}

/**
 * Ajusta los margenes inferiores de las preview-rv para que todas las preview-rv de una fila
 * empiecen a la misma altura. Esto es un problema principalmente cuando una ruta de viaje es
 * compartida ya que la preview-rv tendra una altura mayor a una cuya ruta de viaje no haya sido
 * compartida.
 */
function ajustarPublicaciones() {
  var columnas = $('#expand .fa').hasClass('fa-chevron-left') ? 2 : 3;

  if ($(window).width() < 992) {
    --columnas;
  }

  var publicaciones = $('.publicacion');

  $('#publicaciones').empty();

  for (var i = 0; (i * columnas) < publicaciones.length; ++i) {
    var nuevaFila = $('<div>').addClass('pull-left-100');

    for (var j = 0; j < columnas; ++j) {
      nuevaFila.append(publicaciones[i * columnas + j]);
    }

    $('#publicaciones').append(nuevaFila);
  }
}
