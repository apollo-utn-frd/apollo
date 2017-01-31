import { Component, OnInit } from '@angular/core';
import {FormGroup, Validators, FormBuilder, FormControl} from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../shared/store/state/application.state";
import {LoginFormValidators} from "./loginForm.validators";
import {UserFormVM} from "../shared/models/userForm.vm";
import {storeToUserFormVM} from "./storeToUserFormVM";
import {UpdateUserInfo} from "../shared/store/actions/user.actions";

const V = Validators;

@Component({
  selector: 'apollo-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [NavBarComponent]
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  validators: LoginFormValidators = {
    name: V.compose([V.required, V.minLength(1), V.maxLength(20)]),
    username: V.compose([V.required, V.minLength(4), V.maxLength(20)]),
    description: V.maxLength(150)
  };

  constructor(private fb: FormBuilder, private store: Store<ApplicationState>) { }

  ngOnInit() {
    this.store.select(storeToUserFormVM)
      .subscribe((userForm: UserFormVM) => {
        this.form = new FormGroup({
          nombre: new FormControl(userForm.nombre, this.validators.name),
          apellido: new FormControl(userForm.apellido, this.validators.name),
          username: new FormControl(userForm.username, this.validators.username),
          descripcion: new FormControl('', this.validators.description)
        });
      })
  }

  onSubmit() {
    let userForm: UserFormVM = <UserFormVM> this.form.getRawValue();
    this.store.dispatch(new UpdateUserInfo(userForm));
    //this.userService.update(this.loggedUser, uf);
    //this.userService.edit(this.loggedUser).subscribe(_ => console.log('usuario modificado :)'));
    //this.router.navigateByUrl('/home');
  }
}
