import { Routes, RouterModule } from '@angular/router';

import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CreateRVComponent } from './create-rv/create-rv.component';
import { SearchComponent } from './search/search.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';

const routes: Routes = [
  {
    path: 'welcome',
    component: WelcomeComponent
  },
  {
    path: '',
    redirectTo: '/welcome',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'search',
    component: SearchComponent
  },
  {
    path: 'new/rv',
    component: CreateRVComponent
  },
  {
    path: 'profile/test',
    component: ProfileUserComponent
  }
];

export const routing = RouterModule.forRoot(routes);
