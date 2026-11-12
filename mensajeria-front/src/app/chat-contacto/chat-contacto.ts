import {OnDestroy, AfterViewChecked, ChangeDetectorRef, Component, ElementRef, EventEmitter, HostListener, inject, Input, OnInit, Output, signal, ViewChild, OnChanges, SimpleChanges} from '@angular/core';
import {ContactoService} from '../services/nuevo-contacto.service';
import {BloqueoService} from '../services/bloqueo.service';
import {Mensaje} from '../model/mensaje.model';
import {MensajeService} from '../services/mensaje.service';

@Component({
  selector: 'app-chat-contacto',
  standalone: false,
  templateUrl: './chat-contacto.html',
  styleUrl: './chat-contacto.css',
})
export class ChatContacto implements AfterViewChecked, OnInit, OnChanges, OnDestroy {
  editando = false;
  @Output() volver = new EventEmitter<void>();
  @Input() contacto: any;
  @Input() idUsuario!: number;
  @Output() contactoEliminado = new EventEmitter<void>();
  @Output() contactoActualizado = new EventEmitter<void>();
  @Output() editarMensaje = new EventEmitter<{ id: string; texto: string }>();
  @Input() mensajes: Mensaje[] = [];
  @Input() error = '';
  @Output() enviarMensaje = new EventEmitter<string>();
  @Output() eliminarMensaje = new EventEmitter<string>();

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
  editandoId: string | null = null;
  textoEdicion = '';
  texto = '';
  contactoBloqueado = false;
  verificandoBloqueo = true;
  private intervaloBloqueo?: any;


  menuMensajeId: string | null = null;
  constructor(private contactoService: ContactoService, private bloqueoService: BloqueoService, private mensajeService: MensajeService) {}

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.verificarBloqueo();
    this.intervaloBloqueo = setInterval(() => this.verificarBloqueo(true), 3000);
  }

  ngOnDestroy(): void {
    clearInterval(this.intervaloBloqueo);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['contacto'] && this.contacto) {
      this.verificarBloqueo();
    }
  }


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
        this.contactoBloqueado = true;
        this.cancelarBloquear();
        this.mostrar(`Bloqueaste a ${nombre}`, 'success');
        this.verificarBloqueo(true);
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
    }, 2000);
  }



  onEnviar() {
    if (this.contactoBloqueado || this.verificandoBloqueo) {
      return;
    }

    if (!this.texto.trim()) {
      return;
    }

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

    if (this.contactoBloqueado || this.verificandoBloqueo) {
      input.value = '';
      return;
    }

    const archivo = input.files?.[0];

    if (archivo) this.enviarArchivo.emit(archivo);

    input.value = '';
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

  empezarEditar(m: Mensaje) {
    this.menuMensajeId = null;
    this.editandoId = m.id;
    this.textoEdicion = m.texto ?? '';
  }

  cancelarEdicion() {
    this.editandoId = null;
    this.textoEdicion = '';
  }

  guardarEdicion(m: Mensaje) {
    const t = this.textoEdicion.trim();
    if (!t || t === m.texto) {
      this.cancelarEdicion();
      return;
    }
    this.mensajeService.editar(m.id, this.idUsuario, t).subscribe({
      next: () => {
        m.texto = t;
        m.editado = true;
        this.cancelarEdicion();
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al editar', err);
        this.cancelarEdicion();
      }
    });
  }

  verificarBloqueo(silencioso = false): void {
    if (!silencioso) {
      this.verificandoBloqueo = true;
    }

    this.bloqueoService.listarBloqueados(this.idUsuario).subscribe({
      next: (bloqueados) => {
        const idsQueBloquee = bloqueados.map((b: any) => Number(b.idUsuarioBloqueado));

        this.bloqueoService.listarQuienMeBloqueo(this.idUsuario).subscribe({
          next: (quienesMeBloquearon) => {
            const idsQueMeBloquearon = quienesMeBloquearon.map((b: any) => Number(b.idUsuario));

            this.bloqueadosIds.set([...idsQueBloquee, ...idsQueMeBloquearon]);

            const id = Number(this.contacto.idUsuarioContacto);
            this.contactoBloqueado = idsQueBloquee.includes(id) || idsQueMeBloquearon.includes(id);

            this.verificandoBloqueo = false;
            this.cdr.markForCheck();
          },
          error: (error) => {
            console.error('Error al consultar quién me bloqueó:', error);
            this.verificandoBloqueo = false;
            this.cdr.markForCheck();
          }
        });
      },
      error: (error) => {
        console.error('Error al consultar mis bloqueados:', error);
        this.verificandoBloqueo = false;
        this.cdr.markForCheck();
      }
    });
  }
}
