import { Component, AfterViewInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

declare var $: any;

const V = Validators;

@Component({
  selector: 'apollo-creation-form',
  templateUrl: 'creation-form.component.html',
  styleUrls: ['./creation-form.component.css']
})
export class CreationFormComponent implements AfterViewInit {

  form: FormGroup;

  private nameValidators = V.compose([V.required, V.minLength(2), V.maxLength(20)]);
  private descripValidators = V.maxLength(200);
  private visValidator = V.required;

  constructor() {
    this.form = new FormGroup({
      nombre: new FormControl('', this.nameValidators),
      descripcion: new FormControl('', this.descripValidators),
      visibilidad: new FormControl('PUBLICA', this.visValidator)
    });
  }

  ngAfterViewInit() {
    $('.radio').click(function() {
      $(this).find('input[type=radio]').click();
    });

    $('input[type=radio]').click(function(event:any) {
      event.stopPropagation();
    });
  }

  onSubmit(marcadores:any) {
    let data = this.form.getRawValue();
    console.log("valores a emitir: " + JSON.stringify(data));
    console.log("marcadores: ", JSON.stringify(marcadores));

    console.log('Ruta de viaje creada!');
  }
}
