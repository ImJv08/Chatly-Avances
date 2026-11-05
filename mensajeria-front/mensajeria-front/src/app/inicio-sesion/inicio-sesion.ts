import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import {UsuarioService} from '../services/usuario.service';
import {AuthService} from '../services/auth/auth.service';
import {Usuario} from '../model/usuario.model';

@Component({
  selector: 'app-inicio-sesion',
  standalone: false,
  templateUrl: './inicio-sesion.html',
  styleUrl: './inicio-sesion.css'
})

export class InicioSesion {

  usuario: Usuario = {
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    numeroTelefonico: '',
    fotoPerfilUrl: '',
    contrasenia: ''
  };

  isLoggedIn: boolean = false;
  isConfirmed: boolean = false;
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  verContrasenia: boolean = false;

  constructor(private router: Router , private usuarioService: UsuarioService, private authService: AuthService) {}

  private cdr = inject(ChangeDetectorRef);

  onLogin() {
    if (!this.usuario.numeroTelefonico || !this.usuario.contrasenia) {
      this.mostrar('Todos los espacios son obligatorios', 'error');
      return;
    }

    this.authService.login(this.usuario).subscribe({
      next: (token: string) => {
        if (!token) {
          this.mostrar('No se pudo iniciar sesión, vuelva a intentarlo', 'error');
          return;
        }
        sessionStorage.setItem('telefono', this.usuario.numeroTelefonico);

        this.mostrar('¡Es hora de chatear!', 'success');
        this.usuario.numeroTelefonico = '';
        this.usuario.contrasenia = '';
        this.verContrasenia = false;
        this.isLoggedIn = true;
        this.router.navigate(['/principal']);
      },
      error: () => {
        this.mostrar('Número o contraseña incorrectos', 'error');
      }
    });
  }

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.usuario.numeroTelefonico = '';
    this.usuario.contrasenia = '';
    this.verContrasenia = false;
    this.cdr.markForCheck();
  }

  clearMessage(){
    this.message = "";
    this.messageType = null;
  }
}
