import { Component } from '@angular/core';
import { AuthService, UserService } from '../shared/services/index';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NavBarComponent } from '../shared/components/index';

const V = Validators;

@Component({
    selector: 'apollo-login',
    templateUrl: 'login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [NavBarComponent]
})
export class LoginComponent {
    form: FormGroup
    idToken;
    private nameValidators = V.compose([V.required, V.minLength(1), V.maxLength(20)]);
    private usernameValidators = V.compose([V.required, V.minLength(4), V.maxLength(20)])
    private descriptionValidator = V.maxLength(150)

       constructor(fb: FormBuilder, auth: AuthService) {
        this.form = fb.group({
            "nombre": new FormControl("nombre", this.nameValidators),
            "apellido": new FormControl("apellido", this.nameValidators),
            "username": new FormControl("username", this.usernameValidators),
            "descripcion": new FormControl("", this.descriptionValidator)
        });   
    }
    
    onSubmit() {
        console.log("Valores Ingresados:");
        console.log(JSON.stringify(this.form.value));
        console.log(localStorage.length)
        console.log(localStorage.getItem(this.idToken));
    }
}
