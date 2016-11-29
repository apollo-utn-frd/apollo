import { Component, OnInit } from '@angular/core';
import { AuthService, UserService } from '../shared/services/index';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';
import { Store } from '@ngrx/store';
import { State, getUser } from '../shared/store/index';
import { User, UserForm } from '../shared/models/index';
import { Router } from '@angular/router';

const V = Validators;

@Component({
    selector: 'apollo-login',
    templateUrl: 'login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [NavBarComponent]
})
export class LoginComponent implements OnInit {
    form: FormGroup;
    loggedUser: User;

    private nameValidators = V.compose([V.required, V.minLength(1), V.maxLength(20)]);
    private usernameValidators = V.compose([V.required, V.minLength(4), V.maxLength(20)]);
    private descriptionValidator = V.maxLength(150);

       constructor( private fb: FormBuilder
                  , private auth: AuthService
                  , private store: Store<State>
                  , private userService: UserService
                  , private router: Router
                  ) { }

    ngOnInit() {
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
    }

    onSubmit(a) {
        let uf: UserForm = <UserForm> this.form.getRawValue();
        this.userService.update(this.loggedUser, uf);
        this.userService.edit(this.loggedUser).subscribe(_ => console.log('usuario modificado :)'));
        this.router.navigateByUrl('/home');
    }
}
