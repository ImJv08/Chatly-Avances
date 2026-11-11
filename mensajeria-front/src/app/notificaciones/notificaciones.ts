import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import { NotificacionService } from '../services/notificacion.service';
import { Notificacion} from '../model/notificaciones.model';

@Component({
  selector: 'app-notificaciones',
  standalone: false,
  templateUrl: './notificaciones.html',
  styleUrl: './notificaciones.css'
})
export class Notificaciones implements OnInit {

  notificaciones: Notificacion[] = [];
  idUsuario = Number(localStorage.getItem('idUsuario'));
  filtro: 'todo' | 'leidas' | 'no-leidas' = 'todo';
  modalConfirmar = false;
  message: string = "";
  messageType: 'success' | 'error' | null = null;

  private cdr = inject(ChangeDetectorRef);

  constructor(private notificacionService: NotificacionService) {}

  ngOnInit(): void {
    this.idUsuario = Number(sessionStorage.getItem('idUsuario'));

    console.log('ID del usuario para notificaciones:', this.idUsuario);

    if (this.idUsuario > 0) {

      this.listarNotificaciones();
      this.cdr.markForCheck();
    }
  }

  pedirMarcarTodas(): void {
    const hayNoLeidas = this.notificaciones.some(n => !n.leido);
    if (!hayNoLeidas) {
      this.mostrar('No tienes notificaciones sin leer', 'error');
      return;
    }
    this.modalConfirmar = true;
    this.cdr.markForCheck();
  }

  listarNotificaciones(): void {
    this.notificacionService.listarNotificaciones(this.idUsuario).subscribe({
      next: (data) => {
        console.log('Notificaciones recibidas:', data);
        this.notificaciones = data;
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.error('Error al cargar notificaciones:', error);
      }
    });
  }

  confirmarMarcarTodas(): void {
    this.modalConfirmar = false;
    this.notificacionService.marcarTodasLeidas(this.idUsuario).subscribe({
      next: () => {
        this.notificaciones.forEach(n => n.leido = true);
        this.notificacionService.contarNoLeidas(this.idUsuario).subscribe();
        this.mostrar('Todas las notificaciones quedaron como leídas', 'success');
        this.cdr.markForCheck();
      },
      error: () => this.mostrar('No se pudo marcar como leídas', 'success'),
    });
  }

  obtenerMensaje(aviso: Notificacion): string {
    switch (aviso.tipo) {
      case 'AGREGADO_A_GRUPO':
        return 'Te agregaron a un grupo';

      case 'ELIMINADO_DE_GRUPO':
        return 'Te eliminaron de un grupo';

      case 'CAMBIO_ADMIN_GRUPO':
        return 'Ahora eres administrador de un grupo';

      case 'SALIO_GRUPO':
        return 'Un miembro salió de un grupo';

      case 'NUEVO_ESTADO':
        return 'Un contacto publicó un nuevo estado';

      default:
        return 'Tienes una nueva notificación';
    }
  }

  marcarLeida(n: Notificacion): void {
    this.notificacionService.marcarLeida(n.id).subscribe({
      next: () => {
        n.leido = true;
        this.notificacionService.contarNoLeidas(this.idUsuario).subscribe();
        this.cdr.markForCheck();
      }
    });
  }

  get visibles(): Notificacion[] {
    if (this.filtro === 'leidas') return this.notificaciones.filter(n => n.leido);
    if (this.filtro === 'no-leidas') return this.notificaciones.filter(n => !n.leido);
    return this.notificaciones;
  }


  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.cdr.markForCheck();

    setTimeout(() => {
      this.message = '';
      this.messageType = null;
      this.cdr.detectChanges();
    }, 5000);
  }

}
