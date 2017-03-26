// Modules
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MomentModule } from 'angular2-moment';

// routes
import { routing } from './app.routes';

// Components
import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { NavBarWelcomeComponent } from './welcome/components/index';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MessageCreateRVComponent } from './home/components/index';
import { SearchComponent } from './search/search.component';
import { CreateRVComponent } from './create-rv/create-rv.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';

import {
  CreationFormComponent,
  CreationPanelComponent,
  PrivateUsersModalComponent
} from './create-rv/components/index';

import {
  NavBarComponent,
  UserDropdownComponent,
  NotificationDropdownComponent,
  NotificationItemComponent,
  NavBarLogoComponent,
  ProfileCardComponent,
  PreviewRVComponent,
  PreviewUserComponent,
  CheckboxUserComponent,
  ViewRVComponent,
  CommentComponent
} from './shared/components/index';

// Services
import { AuthService, UserService, RVService } from './shared/services/index';
import { MarkerManager, GoogleMapsAPIWrapper } from 'angular2-google-maps/core';

// Directives
import { GoogleplaceDirective } from 'angular2-google-map-auto-complete/directives/googleplace.directive';
import {SearchLocationTextBox} from './create-rv/components/search-location-textbox/search-location-textbox.component';
import {PostService} from "./shared/services/posts.service";
import {
  userEffectsConf, authEffectsConf, routerStoreConf, storeConf, ngGoogleMapsConf,
  rvEffectsConf, postEffectsConf, storeDevToolsConf
} from "./app.exports";

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    NavBarWelcomeComponent,
    LoginComponent,
    HomeComponent,
    MessageCreateRVComponent,
    CreateRVComponent,
    SearchLocationTextBox,
    CreationFormComponent,
    CreationPanelComponent,
    PrivateUsersModalComponent,
    NavBarComponent,
    UserDropdownComponent,
    NotificationDropdownComponent,
    NotificationItemComponent,
    NavBarLogoComponent,
    ProfileCardComponent,
    PreviewRVComponent,
    PreviewUserComponent,
    CheckboxUserComponent,
    ViewRVComponent,
    SearchComponent,
    CommentComponent,
    ProfileUserComponent,
    GoogleplaceDirective
  ],
  imports: [
    BrowserModule,
    HttpModule,
    CommonModule,
    ReactiveFormsModule,
    MomentModule,
    routing,
    ngGoogleMapsConf,
    storeConf,
    routerStoreConf,
    authEffectsConf,
    userEffectsConf,
    rvEffectsConf,
    postEffectsConf,
    storeDevToolsConf
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
    PostService,
    GoogleMapsAPIWrapper
  ]
})
export class AppModule { }
