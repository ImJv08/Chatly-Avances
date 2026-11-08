import { Routes } from '@angular/router';

import {InicioSesion} from './inicio-sesion/inicio-sesion';
import {Registro} from './registro/registro';
import {ChatsPrincipal} from './chats-principal/chats-principal';
import {Estados} from './estados/estados';
import {Notificaciones} from './notificaciones/notificaciones';

import {authGuard, noAuthGuard} from './services/auth/auth.guard';
import {MenuLateral} from './menu-lateral/menu-lateral';




export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: InicioSesion, canActivate: [noAuthGuard] },
  { path: 'registro', component: Registro, canActivate: [noAuthGuard] },
  {
    path: 'principal',
    component: MenuLateral,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'chats', pathMatch: 'full' },
      { path: 'chats', component: ChatsPrincipal },
      { path: 'estados', component: Estados},
      { path: 'notificaciones', component: Notificaciones},

    ],
  },
  { path: '**', redirectTo: 'login' },
];
