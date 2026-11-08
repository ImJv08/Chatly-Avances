import { Component, OnInit } from '@angular/core';
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

  constructor(private notificacionService: NotificacionService) {}

  ngOnInit(): void {
    this.idUsuario = Number(sessionStorage.getItem('idUsuario'));

    console.log('ID del usuario para notificaciones:', this.idUsuario);

    if (this.idUsuario > 0) {
      this.listarNotificaciones();
    }
  }

  listarNotificaciones(): void {
    this.notificacionService.listarNotificaciones(this.idUsuario).subscribe({
      next: (data) => {
        this.notificaciones = data;
      },
      error: (error) => {
        console.error('Error al cargar notificaciones:', error);
      }
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
}
