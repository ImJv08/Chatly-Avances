import {ChangeDetectorRef, Component, inject, model, OnInit, output} from '@angular/core';
import { Router } from '@angular/router';

import { UsuarioService} from '../services/usuario.service';
import { AuthService} from '../services/auth/auth.service';
import { Usuario} from '../model/usuario.model';


@Component({
  selector: 'app-perfil',
  standalone: false,
  templateUrl: './perfil.html',
  styleUrl: './perfil.css'
})
export class Perfil implements OnInit{

  usuario: Usuario = {
    primerNombre: '',
    segundoNombre: '',
    primerApellido: '',
    segundoApellido: '',
    numeroTelefonico: '',
    fotoPerfilUrl: '',
    contrasenia: ''
  };

  cerrar = output<void>();
  actualizado = output<void>();

  confirmPassword: string = "";
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  verContrasenia: boolean = false;
  verConfirmacion: boolean = false;
  archivo: File | null = null;
  vistaPrevia: string | null = null;
  private cdr = inject(ChangeDetectorRef);

  constructor(private usuarioService: UsuarioService, private router: Router) { }

  ngOnInit() {
    this.usuario.numeroTelefonico = sessionStorage.getItem('telefono') ?? '';

    this.usuarioService.obtenerUsuarioPorTelefono(this.usuario.numeroTelefonico).subscribe(u => {
      this.usuario = u;
      this.vistaPrevia = u.fotoPerfilUrl || null;
      this.usuario = { ...u, contrasenia: '' };
      this.cdr.markForCheck();
    });
  }

  actualizar(){
    if(!this.usuario.primerNombre || !this.usuario.primerApellido){
      this.mostrar('Porfavor complete los campos obligatorios', 'error');
    }

    this.usuarioService.actualizarUsuario(this.usuario, this.archivo).subscribe({
      next: (msg) =>{
        this.mostrar(msg, 'success');
        this.actualizado.emit();
        setTimeout(() => this.cerrar.emit(), 1000);
      },
      error: (err) =>{
        const msg = typeof err.error === 'string' ? err.error : 'Error al actualizar';
        this.mostrar(msg, 'error');
      }

    });
  }
  onCerrar() {
    this.cerrar.emit();
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

  quitarFoto() {
    this.archivo = null;
    this.vistaPrevia = null;
    this.usuario.fotoPerfilUrl = '';
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
