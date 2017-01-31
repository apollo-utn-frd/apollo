
import {FormGroup, FormBuilder, FormControl} from "@angular/forms";
import {LoginFormValidators} from "./loginForm.validators";
import {UserFormVM} from "../shared/models/userForm.vm";

export function createForm(fb: FormBuilder, user: UserFormVM, validators: LoginFormValidators): FormGroup {
  return new FormGroup({
    nombre: new FormControl(user.nombre, validators.name),
    apellido: new FormControl(user.apellido, validators.name),
    username: new FormControl(user.username, validators.username),
    descripcion: new FormControl('', validators.description)
  });
}
