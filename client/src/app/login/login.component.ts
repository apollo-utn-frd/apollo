import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/services/index';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import { User, UserForm } from '../shared/models/index';
import { Router } from '@angular/router';
import {ApplicationState} from "../shared/store/state/application.state";

const V = Validators;

@Component({
  selector: 'apollo-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [NavBarComponent]
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  private nameValidators = V.compose([V.required, V.minLength(1), V.maxLength(20)]);
  private usernameValidators = V.compose([V.required, V.minLength(4), V.maxLength(20)]);
  private descriptionValidator = V.maxLength(150);

  constructor( private fb: FormBuilder
    , private store: Store<ApplicationState>
    , private userService: UserService
    , private router: Router
  ) { }

  ngOnInit() {
    /*
     this.store.let(getUser)
     .subscribe((u: User) => {
     this.loggedUser = u;
     this.form = new FormGroup({
     nombre: new FormControl(u.nombre, this.nameValidators),
     apellido: new FormControl(u.apellido, this.nameValidators),
     username: new FormControl(u.username, this.usernameValidators),
     descripcion: new FormControl('', this.descriptionValidator)
     });
     });
     */
  }

  onSubmit(a:any) {
    let uf: UserForm = <UserForm> this.form.getRawValue();

    //this.userService.update(this.loggedUser, uf);
    //this.userService.edit(this.loggedUser).subscribe(_ => console.log('usuario modificado :)'));
    //this.router.navigateByUrl('/home');
  }
}
