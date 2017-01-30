import { Component, AfterViewInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormsModule } from '@angular/forms';

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

  // Output con los datos del formulario enviados al componente padre.
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();

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

    $('input[type=radio]').click(function(event:any) {
      event.stopPropagation();
    });
  }

  onSubmit() {
    let data = this.form.getRawValue();
    console.log("valores a emitir: " + JSON.stringify(data))
    this.formData.emit(data); // emito los datos del form.

    console.log('Ruta de viaje creada!');
  }
}
