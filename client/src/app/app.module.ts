// Modules
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import {StoreModule, combineReducers} from '@ngrx/store';

// routes
import { routing } from './app.routes';

// Components
import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { NavBarWelcomeComponent } from './welcome/components/index';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MessageCreateRVComponent } from './home/components/index';
import { CreateRVComponent } from './create-rv/create-rv.component';

import {
  CreationFormComponent,
  CreationPanelComponent
} from './create-rv/components/index';

import {
  NavBarComponent,
  NotificationsComponent,
  NotificationItemComponent,
  NavBarLogoComponent,
  ProfileCardComponent,
  PreviewRVComponent
} from './shared/components/index';

// Services
import { AuthService, UserService, RVService } from './shared/services/index';
import { AgmCoreModule, MarkerManager, GoogleMapsAPIWrapper } from 'angular2-google-maps/core';

// Store

// Directives
import { GoogleplaceDirective } from 'angular2-google-map-auto-complete/directives/googleplace.directive';
import {compose} from "@ngrx/core/compose";
import {storeFreeze} from "ngrx-store-freeze";
import {RouterStoreModule} from "@ngrx/router-store";
import {EffectsModule} from "@ngrx/effects";
import {AuthEffectService} from "./shared/store/effects/auth-effects.service";
import {localStorageSync} from "ngrx-store-localstorage";
import {appReducers} from "./shared/store/reducers/app.reducer";
import {INITIAL_APP_STATE} from "./shared/store/state/application.state";

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    NavBarWelcomeComponent,
    LoginComponent,
    HomeComponent,
    MessageCreateRVComponent,
    CreateRVComponent,
    CreationFormComponent,
    CreationPanelComponent,
    NavBarComponent,
    NotificationsComponent,
    NotificationItemComponent,
    NavBarLogoComponent,
    ProfileCardComponent,
    PreviewRVComponent,
    GoogleplaceDirective
  ],
  imports: [
    BrowserModule,
    HttpModule,
    CommonModule,
    ReactiveFormsModule,
    routing,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDSA-Sc8yoe_NIGqOwTGoNPiKge0KRK_wo'
    }),
    StoreModule.provideStore(
      compose(storeFreeze, localStorageSync(['id','token']), combineReducers)(appReducers), INITIAL_APP_STATE),
    RouterStoreModule.connectRouter(),
    EffectsModule.run(AuthEffectService),
    StoreDevtoolsModule.instrumentOnlyWithExtension()
  ],
  entryComponents: [
    AppComponent,
    WelcomeComponent,
    LoginComponent,
    HomeComponent,
    CreateRVComponent
  ],
  bootstrap: [
    AppComponent
  ],
  providers: [
    AuthService,
    UserService,
    MarkerManager,
    RVService,
    GoogleMapsAPIWrapper
  ]
})
export class AppModule { }
