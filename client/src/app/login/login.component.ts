import { Component, OnInit } from '@angular/core';
import {FormGroup, Validators, FormBuilder, FormControl} from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import {ApplicationState} from "../shared/store/state/application.state";
import {LoginFormValidators} from "./loginForm.validators";
import {UserFormVM} from "../shared/models/userForm.vm";
import {storeToUserFormVM} from "./storeToUserFormVM";
import {UpdateUserAction} from "../shared/store/actions/user.actions";
import {User} from "../shared/models/user";
import {storeToUserWithAuthVM, UserWithAuthVM} from "./storeToUserWithAuth.vm";
import {go} from "@ngrx/router-store";

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

  user: UserWithAuthVM;

  constructor(private fb: FormBuilder, private store: Store<ApplicationState>) {

    this.store.select(storeToUserWithAuthVM)
      .subscribe((userWithAuth: UserWithAuthVM) => {
        this.user = userWithAuth
      })
      .unsubscribe();
  }

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
      this.store.dispatch(new UpdateUserAction({user: this.user, form: userForm}));
      this.store.dispatch(go('/home'));
  }
}
