import { Component } from '@angular/core';
import { NavBarComponent } from '../shared/components/index';
import { CreationFormComponent, CreationPanelComponent } from './components/index';
import { RVService, UserService } from '../shared/services/index';
import { RV, User } from '../shared/models/index';
import {RVFormVM} from "../shared/models/rvForm.vm";
import {Point} from "../shared/models/point";
import {ApplicationState} from "../shared/store/state/application.state";
import {Store} from "@ngrx/store";

@Component({
  selector: 'apollo-create-rv',
  templateUrl: 'create-rv.component.html',
  styleUrls: ['./create-rv.component.css'],
  providers: [NavBarComponent, CreationFormComponent, CreationPanelComponent]
})
export class CreateRVComponent {
/*
  usuario: User;
  marcadores: Point[];
*/
  constructor(private store: Store<ApplicationState>) { }
/*
  getMarkers(m: Point[]) {
    this.marcadores = m;
    console.log(m);
  }

  onFormSubmit(formData:any): void {
    console.log(formData);
    let newRV: RVFormVM = {
      nombre: formData.nombre,
      creador: parseInt(this.usuario.id),
      descripcion: formData.descripcion,
      sitios: this.marcadores,
      publico: formData.visibilidad
    };
    console.log(newRV);
    //this.rvService.create(newRV).subscribe(_ => console.log("movida creada"));
  }
  */
}
