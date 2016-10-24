// Modules
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

// routes
import { routing } from './app.routes';

// Components
import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { NavBarComponent } from './shared/components/index';
import { CreateRVComponent } from './create-rv/create-rv.component';
import { CreationFormComponent, CreationPanelComponent } from './create-rv/components/index';

// Services
import { AuthService, UserService } from './shared/services/index';
import { AgmCoreModule, MarkerManager, GoogleMapsAPIWrapper } from 'angular2-google-maps/core';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    LoginComponent,
    HomeComponent,
    NavBarComponent,
    CreateRVComponent,
    CreationFormComponent,
    CreationPanelComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    CommonModule,
    ReactiveFormsModule,
    routing,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDSA-Sc8yoe_NIGqOwTGoNPiKge0KRK_wo'
    })
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
    GoogleMapsAPIWrapper
  ]
})
export class AppModule { }
