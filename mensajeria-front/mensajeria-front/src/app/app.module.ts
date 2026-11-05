import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { App } from './app';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './interceptors/auth.interceptor';

import { InicioSesion } from './inicio-sesion/inicio-sesion';
import { Registro } from './registro/registro';
import { ChatsPrincipal} from './chats-principal/chats-principal';
import { MenuLateral} from './menu-lateral/menu-lateral';
import { Estados } from './estados/estados';
import { Perfil } from './perfil/perfil';
import { Notificaciones} from './notificaciones/notificaciones';
import { Bloqueados } from './bloqueados/bloqueados';
import { NuevoContacto} from './nuevo-contacto/nuevo-contacto';
import { NuevoEstado} from './nuevo-estado/nuevo-estado';
import { NuevoGrupo } from './nuevo-grupo/nuevo-grupo';


import { routes } from './app.routes';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    App,
    InicioSesion,
    Registro,
    ChatsPrincipal,
    MenuLateral,
    Estados,
    Perfil,
    Notificaciones,
    Bloqueados,
    NuevoContacto,
    NuevoEstado,
    NuevoGrupo,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'}),
    FormsModule
  ],
  providers: [
    provideHttpClient(withInterceptors([authInterceptor]))
  ],
  bootstrap: [App]
})
export class AppModule { }
