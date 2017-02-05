import { Component, AfterViewInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {Store} from "@ngrx/store";
import {ApplicationState} from "../../../shared/store/state/application.state";
import {AuthState} from "../../../shared/store/state/auth.state";
import * as _ from 'lodash';
import {CreateRVAction} from "../../../shared/store/actions/rv.actions";
import {go} from "@ngrx/router-store";

declare var $: any;

const V = Validators;

@Component({
  selector: 'apollo-creation-form',
  templateUrl: 'creation-form.component.html',
  styleUrls: ['./creation-form.component.css']
})
export class CreationFormComponent implements AfterViewInit {

  form: FormGroup;
  authData: AuthState;

  private nameValidators = V.compose([V.required, V.minLength(2), V.maxLength(20)]);
  private descripValidators = V.maxLength(200);
  private visValidator = V.required;

  constructor(private store: Store<ApplicationState>) {
    this.store.select((state: ApplicationState) => state.authState)
      .subscribe((auth: AuthState) => { this.authData = auth; })
      .unsubscribe();

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
    let rvFormData = this.form.getRawValue();
    let puntos = _.map(marcadores, toApolloPointRepr);
    this.store.dispatch(new CreateRVAction({rvForm: rvFormData, sitios: puntos, authData: this.authData}));
    this.store.dispatch(go('/home'))
  }
}

function toApolloPointRepr(p:any) {
    return {
      latitud: p.lat,
      longitud: p.lng
    }
  }

