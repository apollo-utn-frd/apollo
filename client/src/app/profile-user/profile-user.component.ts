import { Component, OnInit, AfterViewInit } from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../shared/store/state/application.state";
import {User} from "../shared/models/user";
import {Observable} from "rxjs";

declare var $: any;

@Component({
    selector: 'apollo-home',
    templateUrl: 'profile-user.component.html',
    styleUrls: ['./profile-user.component.css'],
    providers: [NavBarComponent, ProfileCardComponent]
})
export class ProfileUserComponent implements AfterViewInit {

  users$: Observable<User>;

  ngAfterViewInit() {
    ajustarLayout();

    $(window).resize(function() {
      ajustarLayout();
    });

    $('#expand').click(function() {
      $('.profile-card').toggle();
      $('#publicaciones').toggleClass('reduced');
      $(this).find('.fa').toggleClass('fa-chevron-left').toggleClass('fa-chevron-right');

      ajustarLayout();
    });
  }

  constructor(private store: Store<ApplicationState> , private router: Router) {
    this.users$ = Observable.of({
        id: "2017",
        username: "test",
        email: "jonsnow@gmail.com",
        nombre: "Jon",
        apellido: "Snow",
        descripcion: "Rey en el Norte",
        firstLogin: false,
        accountLocked: false,
        imagenUrl: "http://localhost:8080/images/usuario/1.jpg",
        seguidos: [],
        seguidores: [],
        rutasViaje: [1,2,3,4,5,6,7,8,9,10],
        comentarios: [],
        favoritos: [],
        compartidos: [],
        autorizaciones: [],
        dateCreated: new Date(),
    })
  }

}

function ajustarLayout() {
  ajustarPublicaciones();
}

/**
 * Genera la grilla de las publicaciones para que todas las preview-rv de una fila empiecen a la
 * misma altura. Esto es un problema principalmente cuando una ruta de viaje es compartida ya que
 * la preview-rv tendra una altura mayor a una cuya ruta de viaje no haya sido compartida.
 */
function ajustarPublicaciones() {
  var columnas = $('#expand .fa').hasClass('fa-chevron-left') ? 2 : 3;

  if ($(window).width() < 992) {
    --columnas;
  }

  if ($(window).width() < 767 && columnas > 1) {
    --columnas;
  }

  var publicaciones = $('.publicacion:not(.empty)');

  $('#publicaciones').empty();

  for (var i = 0; (i * columnas) < publicaciones.length; ++i) {
    // Agrego el CSS manualmente porque al agregar el elemento directo al DOM no toma los
    // estilos definidos en home.component.css. Cuando se pase este codigo a nativo de Angular
    // se debe obviar ese paso.
    var nuevaFila = $('<div>')
      .addClass('rowx')
      .css({
        'float': 'left',
        'width': '100%',
        'display': 'flex',
        'justify-content': 'space-between'
      });

    for (var j = 0; j < columnas; ++j) {
      // Si la fila no esta completa se la rellena con publicaciones vacias para que las publicaciones
      // que si existen se posicionen correctamente.
      var publicacion = publicaciones[i * columnas + j] || $('<div>').addClass('publicacion empty');

      nuevaFila.append(publicacion);
    }

    $('#publicaciones').append(nuevaFila);

    $('.publicacion.empty').css('width', $('.publicacion').first().width() + 'px');
  }
}
