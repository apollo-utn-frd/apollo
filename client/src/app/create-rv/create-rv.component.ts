import { Component } from '@angular/core';
import { NavBarComponent } from '../shared/components/index';
import { CreationFormComponent, CreationPanelComponent } from './components/index';
import { RVService, UserService } from '../shared/services/index';
import { RV, User } from '../shared/models/index';

@Component({
  selector: 'apollo-create-rv',
  templateUrl: 'create-rv.component.html',
  styleUrls: ['./create-rv.component.css'],
  providers: [NavBarComponent, CreationFormComponent, CreationPanelComponent]
})
export class CreateRVComponent {

  usuario: User;
  marcadores: Point[];

  constructor( private rvService: RVService
    , private usuarioService: UserService) {
    this.usuarioService.get().subscribe(u => this.usuario = u);
  }

  getMarkers(m: Point[]) {
    this.marcadores = m;
    console.log(m);
  }

  onFormSubmit(formData:any): void {
    console.log(formData);
    let newRV: RV = {
      titulo: formData.nombre,
      id_usuario: this.usuario.idGoogle,
      descripcion: formData.descripcion,
      sitios: this.marcadores,
      visibilidad: formData.visibilidad
    };
    console.log(newRV);
    this.rvService.create(newRV).subscribe(_ => console.log("movida creada"));
  }

}

interface Point {
  latitud: number;
  longitud: number;
}

interface Marker {
  lat: number;
  lng: number;
  label?: string;
  draggable?: boolean;
}
