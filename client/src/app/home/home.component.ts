import {Component, ChangeDetectorRef, AfterViewInit} from '@angular/core';
import { NavBarComponent, ProfileCardComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../shared/store/state/application.state";
import {User} from "../shared/models/user";
import {Observable} from "rxjs";
import {UpdatePostsAction} from "../shared/store/actions/post.action";
import {Post} from "../shared/models/post";
import {PreviewRVComponent} from "../shared/components/preview-rv/preview-rv.component";

declare var $: any;

@Component({
    selector: 'apollo-home',
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css'],
    providers: [NavBarComponent, ProfileCardComponent, PreviewRVComponent]
})
export class HomeComponent implements AfterViewInit {

  users$: Observable<User>;
  posts: Post[];

  constructor(private store: Store<ApplicationState>) {
    this.users$ = this.store.select(st => st.storeData.currentUser);
    this.store.dispatch(new UpdatePostsAction());
    this.store.select((state: ApplicationState) => state.uiState.posts)
      .subscribe(posts => this.posts = posts)
  }

   ngAfterViewInit() {
   ajustarLayout();

   $('apollo-preview-rv:eq(0) img').load(function() {
   ajustarMensaje();
   });

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
