import {ChangeDetectorRef, Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import { Router } from '@angular/router';

import { UsuarioService} from '../services/usuario.service';
import { AuthService} from '../services/auth/auth.service';
import { Usuario} from '../model/usuario.model';


@Component({
  selector: 'app-menu-lateral',
  standalone: false,
  templateUrl: './menu-lateral.html',
  styleUrl: './menu-lateral.css'
})
export class MenuLateral {
  modal: 'perfil' | 'cuentas' | null = null;

  usuario?: Usuario;

  @Output() cerrar = new EventEmitter<void>();

  private usuarioService = inject(UsuarioService);
  private cdr = inject(ChangeDetectorRef);
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.cargarUsuario();
  }

  get iniciales(): string {
    if (!this.usuario) return '';
    return (this.usuario.primerNombre[0] + this.usuario.primerApellido[0]).toUpperCase();
  }

  salir() {
    this.authService.longout();
    this.router.navigate(['']);
  }

  cargarUsuario() {
    const tel = sessionStorage.getItem('telefono');
    if (!tel) { this.salir(); return; }
    this.usuarioService.obtenerUsuarioPorTelefono(tel).subscribe({
      next: (u) => { this.usuario = u; this.cdr.markForCheck(); },
      error: () => this.salir()
    });
  }
}
