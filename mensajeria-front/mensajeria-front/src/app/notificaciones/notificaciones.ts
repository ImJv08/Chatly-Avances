import {ChangeDetectorRef, Component, inject, model, OnInit} from '@angular/core';
import { Router } from '@angular/router';

import { UsuarioService} from '../services/usuario.service';
import { AuthService} from '../services/auth/auth.service';
import { Usuario} from '../model/usuario.model';


@Component({
  selector: 'app-notificaciones',
  standalone: false,
  templateUrl: './notificaciones.html',
  styleUrl: './notificaciones.css'
})
export class Notificaciones{


}
