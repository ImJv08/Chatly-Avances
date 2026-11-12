import {ChangeDetectorRef, Component, EventEmitter, inject, OnDestroy, OnInit, Output, signal} from '@angular/core';
import { Router } from '@angular/router';

import { UsuarioService} from '../services/usuario.service';
import { AuthService} from '../services/auth/auth.service';
import { Usuario} from '../model/usuario.model';
import { NotificacionService } from '../services/notificacion.service';
import {MensajeService} from '../services/mensaje.service';


@Component({
  selector: 'app-menu-lateral',
  standalone: false,
  templateUrl: './menu-lateral.html',
  styleUrl: './menu-lateral.css'
})
export class MenuLateral implements OnInit, OnDestroy {
  modal: 'perfil' | 'cuentas' | null = null;

  usuario?: Usuario;

  @Output() cerrar = new EventEmitter<void>();

  private usuarioService = inject(UsuarioService);
  private cdr = inject(ChangeDetectorRef);
  constructor(private authService: AuthService, private router: Router, public notificacionService: NotificacionService,) {}
  private mensajeService = inject(MensajeService);

  totalNoLeidos = signal(0);
  private idUsuario?: number;
  private intervalo?: any;


  ngOnInit() {
    this.cargarUsuario();

    const telefono = sessionStorage.getItem('telefono');
    if (!telefono) return;

    this.usuarioService.obtenerUsuarioPorTelefono(telefono).subscribe(u => {
      this.idUsuario = u.id;
      this.actualizarTotal();
      this.intervalo = setInterval(() => this.actualizarTotal(), 3000);
    });
  }
  private actualizarTotal() {
    if (!this.idUsuario) return;
    this.mensajeService.noLeidos(this.idUsuario).subscribe(n => {
      this.totalNoLeidos.set(Object.values(n).reduce((a, b) => a + b, 0));
    });
  }

  ngOnDestroy() {
    clearInterval(this.intervalo);
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
      next: (u) => {
        this.usuario = u;
        this.notificacionService.contarNoLeidas(u.id!).subscribe();
        this.cdr.markForCheck();
      },
      error: () => this.salir()
    });
  }
}
