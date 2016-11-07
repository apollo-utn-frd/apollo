import { Component, OnInit } from '@angular/core';
import { AuthService, UserService } from '../shared/services/index';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import { State, getUser } from '../shared/store/index';
import { Observable } from 'rxjs/Observable';
import { User } from '../shared/models/index';
import { Router } from '@angular/router';

const V = Validators;

@Component({
    selector: 'apollo-login',
    templateUrl: 'login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [NavBarComponent]
})
export class LoginComponent {
    form: FormGroup;

    private nameValidators = V.compose([V.required, V.minLength(1), V.maxLength(20)]);
    private usernameValidators = V.compose([V.required, V.minLength(4), V.maxLength(20)]);
    private descriptionValidator = V.maxLength(150);

       constructor( fb: FormBuilder
                  , auth: AuthService
                  , store: Store<State>
                  , userService: UserService
                  ) {
        store.let(getUser)
             .subscribe((u: User) => {
                this.form = fb.group({
                    'nombre': new FormControl(u.firstname, this.nameValidators),
                    'apellido': new FormControl(u.lastname, this.nameValidators),
                    'username': new FormControl(u.username, this.usernameValidators),
                    'descripcion': new FormControl('', this.descriptionValidator)
                });
            });
    }

    onSubmit() {
    }
}
