import { Component, AfterViewInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

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

  constructor(fb: FormBuilder) {
    this.form = fb.group({
      'nombre': new FormControl('', this.nameValidators),
      'descripcion': new FormControl('', this.descripValidators),
      'visibilidad': new FormControl('visibilidad', this.visValidator)
    });
  }

  ngAfterViewInit() {
    $('.radio').click(function() {
      $(this).find('input[type=radio]').click();
    });

    $('input[type=radio]').click(function(event) {
      event.stopPropagation();
    });
  }

  onSubmit() {
    // TODO
    // Crear objeto de RV y persistirlo.
    // tener en cuenta que pertenece a un usuario en particular
    console.log('Ruta de viaje creada!');
  }
}
