import { ChangeDetectorRef, Component, inject, output } from '@angular/core';
import { EstadoService } from '../services/estado.service';

@Component({
  selector: 'app-nuevo-estado',
  standalone: false,
  templateUrl: './nuevo-estado.html',
  styleUrl: './nuevo-estado.css'
})

export class NuevoEstado {
  cerrar = output<void>();
  publicado = output<void>();

  archivo: File | null = null;
  vistaPrevia: string | null = null;
  esVideo = false;
  descripcion = '';
  message = '';

  private estadoService = inject(EstadoService);
  private cdr = inject(ChangeDetectorRef);

  onArchivo(e: Event) {
    const f = (e.target as HTMLInputElement).files?.[0];
    if (!f) return;
    const img = f.type.startsWith('image/');
    const vid = f.type.startsWith('video/');
    if (!img && !vid) { this.message = 'Elige una imagen o un video'; return; }
    if (f.size > (img ? 5 : 30) * 1024 * 1024) {
      this.message = img ? 'La imagen pesa más de 5 MB' : 'El video pesa más de 30 MB';
      return;
    }
    this.message = '';
    this.archivo = f;
    this.esVideo = vid;
    this.vistaPrevia = URL.createObjectURL(f);
    this.cdr.markForCheck();
  }

  publicar() {
    if (!this.archivo) { this.message = 'Elige una imagen o un video'; return; }
    const idUsuario = Number(sessionStorage.getItem('idUsuario'));
    this.estadoService.crearEstado(idUsuario, this.descripcion.trim(), this.archivo).subscribe({
      next: () => { this.publicado.emit(); this.cerrar.emit(); },
      error: (err) => {
        this.message = typeof err.error === 'string' ? err.error : 'No se pudo publicar';
        this.cdr.markForCheck();
      }
    });
  }
}
