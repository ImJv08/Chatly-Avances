import { ChangeDetectorRef, Component, EventEmitter, inject, OnDestroy, OnInit, Output } from '@angular/core';
import { BloqueoService } from '../services/bloqueo.service';
import { Bloqueo } from '../model/bloqueo.model';

@Component({
  selector: 'app-bloqueados',
  standalone: false,
  templateUrl: './bloqueados.html',
  styleUrl: './bloqueados.css',
})
export class Bloqueados implements OnInit, OnDestroy {
  @Output() cerrar = new EventEmitter<void>();

  bloqueados: Bloqueo[] = [];
  idUsuario = Number(sessionStorage.getItem('idUsuario'));
  message: string = '';
  messageType: 'success' | 'error' | null = null;
  cargando = true;

  private cdr = inject(ChangeDetectorRef);
  private temporizadorMensaje: any = null;

  constructor(private bloqueoService: BloqueoService) {}

  ngOnInit(): void {
    if (!this.idUsuario) {
      this.cargando = false;
      this.mostrar('No se encontró la sesión del usuario', 'error');
      return;
    }
    this.listarBloqueados();
  }

  ngOnDestroy(): void {
    if (this.temporizadorMensaje) {
      clearTimeout(this.temporizadorMensaje);
    }
  }

  listarBloqueados(): void {
    this.bloqueoService.listarBloqueados(this.idUsuario).subscribe({
      next: (lista) => {
        this.bloqueados = lista;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (error) => {
        this.cargando = false;
        this.mostrar(error.error?.mensaje || 'No se pudo cargar la lista de bloqueados', 'error');
      }
    });
  }

  desbloquear(bloqueo: Bloqueo): void {
    this.bloqueoService.desbloquear(this.idUsuario, bloqueo.idUsuarioBloqueado).subscribe({
      next: () => {
        this.bloqueados = this.bloqueados.filter(
          b => b.idUsuarioBloqueado !== bloqueo.idUsuarioBloqueado
        );
        this.mostrar('Usuario desbloqueado', 'success');
      },
      error: (error) => {
        this.mostrar(error.error?.mensaje || 'No se pudo desbloquear', 'error');
      }
    });
  }

  nombreDe(bloqueo: Bloqueo): string {
    return bloqueo.nombreBloqueado?.trim() || bloqueo.numeroTelefonicoBloqueado;
  }

  inicialDe(bloqueo: Bloqueo): string {
    return this.nombreDe(bloqueo).charAt(0).toUpperCase();
  }

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.cdr.markForCheck();

    if (this.temporizadorMensaje) {
      clearTimeout(this.temporizadorMensaje);
    }
    this.temporizadorMensaje = setTimeout(() => {
      this.message = '';
      this.messageType = null;
      this.cdr.markForCheck();
    }, 3000);
  }
}