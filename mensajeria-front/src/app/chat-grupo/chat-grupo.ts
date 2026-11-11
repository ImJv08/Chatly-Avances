import {
  ChangeDetectorRef,
  Component, ElementRef,
  EventEmitter, HostListener,
  inject,
  Input,
  OnChanges,
  OnInit,
  Output,
  signal, SimpleChanges, ViewChild
} from '@angular/core';
import {GrupoService} from '../services/nuevo-grupo.service';
import {ContactoService} from '../services/nuevo-contacto.service';
import {Contacto} from '../model/contacto.model';
import {catchError, forkJoin, map, of} from 'rxjs';
import {Mensaje} from '../model/mensaje.model';

@Component({
  selector: 'app-chat-grupo',
  standalone: false,
  templateUrl: './chat-grupo.html',
  styleUrl: './chat-grupo.css',
})
export class ChatGrupo implements OnChanges {
  verInfo = false;
  @Output() volver = new EventEmitter<void>();
  @Input() grupo: any;
  @Output() grupoEliminado = new EventEmitter<void>();
  @Input() idUsuario!: number;
  @Input() contactos: any[] = [];
  @Output() miembrosCambiados = new EventEmitter<void>();
  @Output() actualizado = new EventEmitter<void>();
  @Input() mensajes: Mensaje[] = [];
  @Input() error = '';
  @Output() enviarMensaje = new EventEmitter<string>();


  texto = '';

  private cdr = inject(ChangeDetectorRef);

  constructor(private grupoService: GrupoService, private contactoService: ContactoService) {}

  grupos = signal<any[]>([]);
  grupoSeleccionado: any = null;
  mostrarConfirmacionGrupo = false;
  mostrarActualizarGrupo = false;
  nombreGrupoEditar = '';
  fotoGrupoEditar: File | null = null;
  grupoParaSalir: any = null;
  miembroSeleccionado: any = null;
  mostrarConfirmacionMiembro = false;
  miembros: any[] = [];
  contactosDisponibles: Contacto[] = [];
  miembroParaAgregar: Contacto | null = null;
  mostrarModalAgregarMiembro = false;
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  mostrarAgregar = false;
  seleccionados = new Set<number>();
  mostrarConfirmacionSalir = false;
  mostrarConfirmacionAdmin = false;
  nombreEditar = '';
  archivoGrupo: File | null = null;
  vistaPreviaGrupo: string | null = null;
  @Output() eliminarMensaje = new EventEmitter<string>();

  menuMensajeId: string | null = null;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['grupo'] && this.grupo?.id) {
      this.listarMiembrosGrupo();
    }
  }

  get disponibles() {
    const yaEstan = new Set(this.miembros.map(m => m.idUsuario));
    return this.contactos.filter(c => !yaEstan.has(c.idUsuarioContacto));
  }

  abrirAgregar() {
    this.seleccionados.clear();
    this.message = '';
    this.mostrarAgregar = true;
  }

  cerrarAgregar() {
    this.mostrarAgregar = false;
  }

  alternar(id: number) {
    this.seleccionados.has(id) ? this.seleccionados.delete(id) : this.seleccionados.add(id);
  }

  cargarMiembros() {
    this.grupoService.listarMiembrosGrupo(this.grupo.id).subscribe({
      next: (miembros) => {
        this.miembros = miembros;
        this.cdr.detectChanges();
      },
    });
  }

  agregarSeleccionados() {
    if (!this.seleccionados.size) {
      this.message = 'Elige al menos un contacto';
      this.messageType = 'error';
      return;
    }

    const peticiones = [...this.seleccionados].map(id =>
      this.grupoService.agregarMiembro(this.grupo.id, id, this.idUsuario).pipe(
        map(() => ({ id, ok: true, mensaje: '' })),
        catchError(err => of({
          id,
          ok: false,
          mensaje: err.error?.mensaje || err.error?.message || 'Error al agregar',
        }))
      )
    );

    forkJoin(peticiones).subscribe(resultados => {
      const fallidos = resultados.filter(r => !r.ok);

      this.cargarMiembros();
      this.miembrosCambiados.emit();

      if (!fallidos.length) {
        this.cerrarAgregar();
      } else {
        resultados.filter(r => r.ok).forEach(r => this.seleccionados.delete(r.id));

        const nombres = fallidos.map(f => this.nombreContacto(f.id)).join(', ');
        this.message = `No se pudo agregar a: ${nombres}. ${fallidos[0].mensaje}`;
        this.messageType = 'error';
      }
      this.cdr.detectChanges();
    });
  }

  nombreContacto(id: number): string {
    const c = this.contactos.find(x => x.idUsuarioContacto === id);
    return c?.nombrePersonalizado || c?.telefono || 'un contacto';
  }

  nombreMiembro(miembro: any): string {
    if (miembro.idUsuario === this.idUsuario) return 'Tú';

    const contacto = this.contactos.find(
      c => c.idUsuarioContacto === miembro.idUsuario
    );

    return contacto?.nombrePersonalizado
      || contacto?.telefono
      || miembro.telefono
      || miembro.nombreCompleto;
  }

  inicial(miembro: any): string {
    const base = miembro.idUsuario === this.idUsuario
      ? miembro.nombreCompleto
      : this.nombreMiembro(miembro);
    return (base || '?').charAt(0).toUpperCase();
  }

  listarGrupos(idUsuario: number): void {
    this.grupoService.listarGruposPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        console.log('GRUPOS RECIBIDOS:', respuesta);
        this.grupos.set(respuesta);
      },
      error: (error) => {
        console.error('Error al listar grupos:', error);
      }
    });
  }

  confirmarEliminarGrupo(): void {
    this.message = '';
    this.messageType = null;
    this.mostrarConfirmacionGrupo = true;
  }

  cancelarEliminarGrupo(): void {
    this.mostrarConfirmacionGrupo = false;
    this.message = '';
    this.messageType = null;
  }


  listarGruposConRol(idUsuario: number): void {
    this.grupoService.listarGruposConRol(idUsuario).subscribe({
      next: (grupos) => {
        this.grupos.set(grupos);
        console.log('Grupos con rol:', grupos);
      },
      error: (error) => {
        console.error('Error al listar grupos con rol:', error);
      }
    });
  }

  eliminarGrupoSeleccionado(): void {
    if (!this.grupo) return;

    this.grupoService.eliminarGrupo(this.grupo.id, this.idUsuario).subscribe({
      next: () => {
        this.mostrarConfirmacionGrupo = false;
        this.grupoEliminado.emit();
      },
      error: (error) => {
        this.message =
          error.error?.mensaje ||
          error.error?.message ||
          'Error al eliminar el grupo';

        this.messageType = 'error';
      }
    });
  }

  abrirActualizarGrupo(grupo: any): void {
    this.grupoSeleccionado = grupo;
    this.nombreGrupoEditar = grupo.nombre;
    this.fotoGrupoEditar = null;
    this.mostrarActualizarGrupo = true;
  }

  seleccionarFotoGrupo(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.fotoGrupoEditar = input.files?.[0] ?? null;
  }

  cancelarActualizarGrupo(): void {
    this.mostrarActualizarGrupo = false;
    this.grupoSeleccionado = null;
    this.fotoGrupoEditar = null;
  }

  guardarCambiosGrupo(): void {
    if (!this.grupoSeleccionado || !this.nombreGrupoEditar.trim()) {
      return;
    }


    const grupo = {
      id: this.grupoSeleccionado.id,
      nombreGrupo: this.nombreGrupoEditar.trim(),
      fotoPerfilUrl: this.grupoSeleccionado.fotoPerfilUrl
    };

    this.grupoService.actualizarGrupo(this.idUsuario, grupo).subscribe({
      next: () => {
        this.listarGruposConRol(this.idUsuario);
        this.cancelarActualizarGrupo();
      },
      error: (error) => {
        console.error('Error al actualizar el grupo:', error);
      }
    });

  }

  listarMiembrosGrupo(): void {
    this.grupoService.listarMiembrosGrupo(this.grupo.id).subscribe({
      next: (respuesta) => {
        console.log('MIEMBROS:', respuesta);
        this.miembros = respuesta;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error al listar los miembros del grupo', error);
      }
    });
  }

  confirmarEliminarMiembro(miembro: any): void {
    this.miembroSeleccionado = miembro;
    this.mostrarConfirmacionMiembro = true;
  }

  cancelarEliminarMiembro(): void {
    this.miembroSeleccionado = null;
    this.mostrarConfirmacionMiembro = false;
  }


  eliminarMiembro(): void {
    if (!this.miembroSeleccionado) return;

    this.grupoService.eliminarMiembroGrupo(
      this.grupo.id,
      this.miembroSeleccionado.idUsuario,
      this.idUsuario
    ).subscribe({
      next: () => {
        this.mostrarConfirmacionMiembro = false;
        this.miembroSeleccionado = null;
        this.listarMiembrosGrupo();
      },
      error: (error) => {
        console.error('Error al eliminar miembro:', error);
      }
    });
  }

  abrirModalAgregarMiembro(): void {
    this.mostrarModalAgregarMiembro = true;
    this.miembroParaAgregar = null;

    this.contactoService.listarContactosPorUsuario(this.idUsuario).subscribe({
      next: (respuesta: Contacto[]) => {
        this.contactosDisponibles = respuesta.filter(contacto =>
          !this.miembros.some(
            miembro => miembro.idUsuario === contacto.idUsuarioContacto
          )
        );
      },
      error: (error) => {
        console.error('Error al consultar contactos:', error);
      }
    });
  }

  cancelarAgregarMiembro(): void {
    this.mostrarModalAgregarMiembro = false;
    this.miembroParaAgregar = null;
  }

  seleccionarMiembro(contacto: Contacto): void {
    this.miembroParaAgregar = contacto;
  }

  agregarMiembroSeleccionado(): void {
    if (!this.miembroParaAgregar) return;

    this.grupoService.agregarMiembro(
      this.grupo.id,
      this.miembroParaAgregar.idUsuarioContacto,
      this.idUsuario
    ).subscribe({
      next: () => {
        this.cancelarAgregarMiembro();
        this.listarMiembrosGrupo();
      },
      error: (error) => {
        console.error('Error al agregar miembro:', error);
      }
    });
  }

  confirmarSalirGrupo(): void {
    this.mostrarConfirmacionSalir = true;
  }

  cancelarSalirGrupo(): void {
    this.mostrarConfirmacionSalir = false;
  }

  salirDelGrupo(): void {
    this.grupoService.salirGrupo(
      this.grupo.id,
      this.idUsuario
    ).subscribe({
      next: () => {
        this.mostrarConfirmacionSalir = false;
        this.grupoEliminado.emit();
        this.volver.emit();
      },
      error: (error) => {
        console.error('Error al salir del grupo:', error);
      }
    });
  }

  confirmarHacerAdmin(miembro: any): void {
    this.miembroSeleccionado = miembro;
    this.mostrarConfirmacionAdmin = true;
  }

  cancelarHacerAdmin(): void {
    this.miembroSeleccionado = null;
    this.mostrarConfirmacionAdmin = false;
  }

  hacerAdmin(): void {
    if (!this.miembroSeleccionado) return;

    this.grupoService.cambiarRolAdmin(
      this.grupo.id,
      this.miembroSeleccionado.idUsuario,
      this.idUsuario
    ).subscribe({
      next: () => {
        this.mostrarConfirmacionAdmin = false;
        this.miembroSeleccionado = null;
        this.listarMiembrosGrupo();
      },
      error: (error) => {
        console.error('Error al cambiar el rol:', error);
      }
    });
  }

  abrirInfo(): void {
    this.nombreEditar = this.grupo.nombreGrupo || this.grupo.nombre || '';
    this.archivoGrupo = null;
    this.vistaPreviaGrupo = null;
    this.message = '';
    this.verInfo = true;
  }

  onFotoGrupo(e: Event): void {
    const f = (e.target as HTMLInputElement).files?.[0];
    if (!f) return;
    if (!f.type.startsWith('image/') || f.size > 5 * 1024 * 1024) {
      this.mostrar('Elige una imagen de máximo 5 MB', 'error');
      return;
    }
    this.archivoGrupo = f;
    this.vistaPreviaGrupo = URL.createObjectURL(f);
    this.cdr.markForCheck();
  }

  guardarGrupo(): void {
    if (!this.nombreEditar.trim()) {
      this.mostrar('El grupo necesita un nombre', 'error');
      return;
    }
    const dto = {
      id: this.grupo.id,
      nombreGrupo: this.nombreEditar.trim(),
      fotoPerfilUrl: this.grupo.fotoPerfilUrl ?? ''
    };
    this.grupoService.actualizarGrupo(this.idUsuario, dto, this.archivoGrupo).subscribe({
      next: () => {
        this.archivoGrupo = null;
        this.vistaPreviaGrupo = null;
        this.verInfo = false;
        this.actualizado.emit();
      },
      error: (err) => this.mostrar(
        typeof err.error === 'string' ? err.error : 'No se pudo actualizar el grupo', 'error')
    });
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
  nombreEmisor(id: number): string {
    const miembro = this.miembros?.find((x: any) => x.idUsuario === id);
    return miembro ? this.nombreMiembro(miembro) : 'Usuario ' + id;
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
