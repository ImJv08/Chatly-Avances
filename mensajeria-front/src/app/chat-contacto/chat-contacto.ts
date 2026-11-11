import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter, HostListener,
  inject,
  Input,
  Output,
  signal,
  ViewChild
} from '@angular/core';
import {ContactoService} from '../services/nuevo-contacto.service';
import {BloqueoService} from '../services/bloqueo.service';
import {Mensaje} from '../model/mensaje.model';

@Component({
  selector: 'app-chat-contacto',
  standalone: false,
  templateUrl: './chat-contacto.html',
  styleUrl: './chat-contacto.css',
})
export class ChatContacto implements AfterViewChecked{
  editando = false;
  @Output() volver = new EventEmitter<void>();
  @Input() contacto: any;
  @Input() idUsuario!: number;
  @Output() contactoEliminado = new EventEmitter<void>();
  @Output() contactoActualizado = new EventEmitter<void>();

  contactos = signal<any[]>([]);
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  nombreContactoEditar = '';
  contactoParaBloquear: any = null;
  bloqueadosIds = signal<number[]>([]);
  contactoSeleccionado: any = null;
  mostrarConfirmacion = false;
  mostrarActualizarContacto = false;
  mostrarConfirmacionBloqueo = false;
  menuAbierto: string | null = null;
  @Input() mensajes: Mensaje[] = [];
  @Input() error = '';
  @Output() enviarMensaje = new EventEmitter<string>();
  texto = '';
  @Output() eliminarMensaje = new EventEmitter<string>();

  menuMensajeId: string | null = null;
  constructor(private contactoService: ContactoService, private bloqueoService: BloqueoService) {}

  private cdr = inject(ChangeDetectorRef);



  confirmarEliminarContacto(contacto: any): void {
    this.contactoSeleccionado = contacto;
    this.mostrarConfirmacion = true;
  }

  cancelarEliminarContacto(): void {
    this.mostrarConfirmacion = false;
    this.contactoSeleccionado = null;
  }

  eliminarContacto(idUsuario: number, idUsuarioContacto: number): void {
    this.message = '';
    this.messageType = null;

    this.contactoService.eliminarContacto(idUsuario, idUsuarioContacto).subscribe({
      next: () => {
        this.mostrarConfirmacion = false;
        this.contactoSeleccionado = null;
        this.message = '';
        this.messageType = null;

        this.contactoEliminado.emit();
      },
      error: (error) => {
        this.mostrar(
          error.error?.mensaje ||
          error.error?.message ||
          'Error al eliminar contacto',
          'error'
        );
      }
    });
  }

  actualizarContacto(id: number, nombre: string): void {
    if (!nombre.trim()) {
      this.mostrar('Debe digitar el nuevo nombre que le desea colocar', 'error');
      return;
    }

    this.contactoService.actualizarNombreContacto(
      this.idUsuario,
      this.contacto.idUsuarioContacto,
      nombre.trim()
    ).subscribe({
      next: () => {
        this.contacto.nombrePersonalizado = nombre.trim();

        this.editando = false;
        this.nombreContactoEditar = '';
        this.message = '';
        this.messageType = null;

        this.contactoActualizado.emit();
      },
      error: (error) => {
        this.mostrar(
          error.error?.mensaje ||
          error.error?.message ||
          'Error al actualizar el contacto',
          'error'
        );
      }
    });
  }

  listarContactos(idUsuario: number): void {
    this.contactoService.listarContactosPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        console.log('CONTACTOS RECIBIDOS:', respuesta);
        this.contactos.set(respuesta);
      },
      error: (error) => console.error('Error al listar contactos', error)
    });
  }

  bloquearContactoSeleccionado(): void {
    if (!this.contactoParaBloquear) return;

    const idBloqueado = this.contactoParaBloquear.idUsuarioContacto;
    const nombre = this.contactoParaBloquear.nombrePersonalizado || this.contactoParaBloquear.telefono;

    this.bloqueoService.bloquear(this.idUsuario, idBloqueado).subscribe({
      next: () => {
        this.bloqueadosIds.update(ids => [...ids, idBloqueado]);
        this.cancelarBloquear();
        this.mostrar(`Bloqueaste a ${nombre}`, 'success');
      },
      error: (error) => {
        this.cancelarBloquear();
        this.mostrar(error.error?.mensaje || 'No se pudo bloquear al usuario', 'error');
      }
    });
  }

  confirmarBloquear(contacto: any): void {
    this.contactoParaBloquear = contacto;
    this.mostrarConfirmacionBloqueo = true;
    this.menuAbierto = null;
  }

  cancelarBloquear(): void {
    this.mostrarConfirmacionBloqueo = false;
    this.contactoParaBloquear = null;
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



  onEnviar() {
    if (!this.texto.trim()) return;
    this.enviarMensaje.emit(this.texto);
    this.texto = '';
  }

  get visto(): boolean {
    const ultimo = this.mensajes[this.mensajes.length - 1];
    return !!ultimo && ultimo.idEmisor === this.idUsuario && ultimo.leido;
  }

  @ViewChild('contenedor') contenedor!: ElementRef<HTMLElement>;
  private ultimoTotal = 0;

  ngAfterViewChecked() {
    if (this.mensajes.length !== this.ultimoTotal) {
      this.ultimoTotal = this.mensajes.length;
      const el = this.contenedor?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    }
  }

  @Output() enviarArchivo = new EventEmitter<File>();

  onImagen(e: Event) {
    const input = e.target as HTMLInputElement;
    const archivo = input.files?.[0];
    if (archivo) this.enviarArchivo.emit(archivo);
    input.value = '';   // permite elegir la misma imagen otra vez
  }

  descargar(m: Mensaje) {
    if (!m.contenidoUrl) return;
    fetch(m.contenidoUrl)
      .then(r => r.blob())
      .then(blob => {
        const enlace = document.createElement('a');
        enlace.href = URL.createObjectURL(blob);
        enlace.download = m.nombreArchivo || 'archivo';
        enlace.click();
        URL.revokeObjectURL(enlace.href);
      });
  }

  toggleMenuMensaje(id: string, event: MouseEvent) {
    event.stopPropagation();
    this.menuMensajeId = this.menuMensajeId === id ? null : id;
  }

  @HostListener('document:click')
  cerrarMenuMensaje() {
    this.menuMensajeId = null;
  }

  borrar(m: Mensaje) {
    this.menuMensajeId = null;
    this.eliminarMensaje.emit(m.id);
  }
}
