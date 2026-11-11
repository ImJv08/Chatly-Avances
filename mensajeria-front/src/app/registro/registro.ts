import {ChangeDetectorRef, Component, inject} from '@angular/core';
import {Router} from '@angular/router';
import {UsuarioService} from '../services/usuario.service';
import {AuthService} from '../services/auth/auth.service';
import {Usuario} from '../model/usuario.model';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.html',
  styleUrl: './registro.css'
})

export class Registro {
  usuario: Usuario = {
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    numeroTelefonico: '',
    fotoPerfilUrl: '',
    contrasenia: ''
  };

  confirmPassword: string = "";
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  verContrasenia: boolean = false;
  verConfirmacion: boolean = false;
  archivo: File | null = null;
  vistaPrevia: string | null = null;

  private cdr = inject(ChangeDetectorRef);

  constructor(private router: Router , private usuarioService: UsuarioService, private authService: AuthService) {}

  onSignUp() {
    const passwordRegex = /^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{7,}$/;
    const numeroRegex = /^\d{10}$/;

    if (!this.usuario.primerNombre || !this.usuario.primerApellido || !this.usuario.numeroTelefonico
      || !this.usuario.contrasenia || !this.confirmPassword) {
      this.mostrar('Debe llenar todos los datos obligatorios', 'error');
    } else if (this.usuario.contrasenia != this.confirmPassword) {
      this.mostrar('Las contraseñas no coinciden', 'error');
    } else if (!passwordRegex.test(this.usuario.contrasenia)) {
      this.mostrar('La contraseña debe tener mínimo 7 caracteres, una mayúscula y un carácter especial', 'error');
    } else if (!numeroRegex.test(this.usuario.numeroTelefonico)) {
      this.mostrar('El número telefónico debe tener exactamente 10 dígitos', 'error');
    } else if (this.archivo) {
      const foto = this.archivo;
      this.authService.subirFoto(foto).subscribe({
        next: (url) => { this.usuario.fotoPerfilUrl = url; this.registrar(); },
        error: () => this.mostrar('No se pudo subir la foto', 'error')
      });
    } else {
      this.registrar();
    }
  }

  private registrar() {
    this.authService.registrar(this.usuario).subscribe({
      next: (msg: any) => {
        sessionStorage.setItem('telefono', this.usuario.numeroTelefonico);

        this.mostrar(msg, 'success');
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        const msg = typeof err.error === 'string' ? err.error : 'Error al registrar el usuario';
        this.mostrar(msg, 'error');
      }
    });
  }

  onFoto(e: Event) {
    const f = (e.target as HTMLInputElement).files?.[0];
    if (!f) return;
    if (!f.type.startsWith('image/') || f.size > 5 * 1024 * 1024) {
      this.mostrar('Elige una imagen de máximo 5 MB', 'error');
      return;
    }
    this.archivo = f;
    this.vistaPrevia = URL.createObjectURL(f);
    this.cdr.markForCheck();
  }

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.usuario.numeroTelefonico = '';
    this.usuario.contrasenia = '';
    this.confirmPassword = '';
    this.archivo = null;
    this.verContrasenia = false;
    this.verConfirmacion = false;
    this.cdr.markForCheck();

    setTimeout(() => {
      this.message = '';
      this.messageType = null;
      this.cdr.detectChanges();
    }, 5000);
  }
}
