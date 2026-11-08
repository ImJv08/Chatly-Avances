import {ChangeDetectorRef, Component, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {ContactoService} from '../services/nuevo-contacto.service';
import {UsuarioService} from '../services/usuario.service';
import {GrupoService} from '../services/nuevo-grupo.service';
import {BloqueoService} from '../services/bloqueo.service';

@Component({
  selector: 'app-chats-principal',
  standalone: false,
  templateUrl: './chats-principal.html',
  styleUrl: './chats-principal.css'
})
export class ChatsPrincipal implements OnInit, OnDestroy {

  modal: 'contacto' | 'grupo' | 'bloqueados' | null = null;
  verBloqueados = false;
  contactos = signal<any[]>([]);
  idUsuario!: number;
  grupos = signal<any[]>([]);
  bloqueadosIds = signal<number[]>([]);
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  mostrarConfirmacion = false;
  contactoSeleccionado: any = null;
  mostrarConfirmacionGrupo = false;
  grupoSeleccionado: any = null;
  nombreUsuario: string = sessionStorage.getItem('nombreUsuario') || '';
  mostrarConfirmacionSalir = false;
  grupoParaSalir: any = null;
  mostrarActualizarContacto = false;
  nombreContactoEditar = '';
  mostrarConfirmacionBloqueo = false;
  contactoParaBloquear: any = null;

  private cdr = inject(ChangeDetectorRef);
  private temporizadorMensaje: any = null;

  constructor(
    private contactoService: ContactoService,
    private usuarioService: UsuarioService,
    private grupoService: GrupoService,
    private bloqueoService: BloqueoService
  ) {}


  ngOnInit(): void {
    const telefono = sessionStorage.getItem('telefono');

    if (!telefono) {
      console.error('No se encontró el teléfono en la sesión');
      return;
    }

    this.usuarioService.obtenerUsuarioPorTelefono(telefono).subscribe({
      next: (usuario) => {
        this.idUsuario = usuario.id;

        this.listarContactos(this.idUsuario);
        this.listarGruposConRol(this.idUsuario);
        this.cargarBloqueados();
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.temporizadorMensaje) {
      clearTimeout(this.temporizadorMensaje);
    }
  }

  cargarBloqueados(): void {
    this.bloqueoService.listarBloqueados(this.idUsuario).subscribe({
      next: (lista) => {
        this.bloqueadosIds.set(lista.map(b => b.idUsuarioBloqueado));
      },
      error: (error) => console.error('Error al cargar bloqueados', error)
    });
  }

  estaBloqueado(contacto: any): boolean {
    return this.bloqueadosIds().includes(contacto.idUsuarioContacto);
  }

  cerrarBloqueados(): void {
    this.verBloqueados = false;
    this.cargarBloqueados();
  }

  listarContactos(idUsuario: number): void {
    this.contactoService.listarContactosPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        console.log('CONTACTOS RECIBIDOS:', respuesta);
        this.contactos.set(respuesta);
        this.menuAbierto = null;
      },
      error: (error) => console.error('Error al listar contactos', error)
    });
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
  eliminarContacto(idUsuario: number, idUsuarioContacto: number): void {
    this.contactoService.eliminarContacto(idUsuario, idUsuarioContacto).subscribe({
      next: () => {
        this.listarContactos(idUsuario);
      },
      error: (error) => {
        this.mostrar(error.error?.mensaje || error.error?.message || 'Error al eliminar contacto', 'error')
      }
    });
  }

  menuAbierto: string | null = null;

  toggleMenu(id: string, event: MouseEvent): void {
    event.stopPropagation();
    this.menuAbierto = this.menuAbierto === id ? null : id;
  }

  confirmarEliminarContacto(contacto: any): void {
    this.contactoSeleccionado = contacto;
    this.mostrarConfirmacion = true;
  }

  cancelarEliminarContacto(): void {
    this.mostrarConfirmacion = false;
    this.contactoSeleccionado = null;
  }

  eliminarContactoSeleccionado(): void {
    if (!this.contactoSeleccionado) return;

    this.contactoService.eliminarContacto(
      this.idUsuario,
      this.contactoSeleccionado.idUsuarioContacto
    ).subscribe({
      next: () => {
        this.listarContactos(this.idUsuario);
        this.cancelarEliminarContacto();

      },
      error: (error) => {
        console.error('Error al eliminar contacto:', error);
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

  desbloquearContacto(contacto: any): void {
    const idBloqueado = contacto.idUsuarioContacto;
    const nombre = contacto.nombrePersonalizado || contacto.telefono;
    this.menuAbierto = null;

    this.bloqueoService.desbloquear(this.idUsuario, idBloqueado).subscribe({
      next: () => {
        this.bloqueadosIds.update(ids => ids.filter(id => id !== idBloqueado));
        this.mostrar(`Desbloqueaste a ${nombre}`, 'success');
      },
      error: (error) => {
        this.mostrar(error.error?.mensaje || 'No se pudo desbloquear al usuario', 'error');
      }
    });
  }

  confirmarEliminarGrupo(grupo: any): void {
    this.grupoSeleccionado = grupo;
    this.mostrarConfirmacionGrupo = true;
  }

  cancelarEliminarGrupo(): void {
    this.mostrarConfirmacionGrupo = false;
    this.grupoSeleccionado = null;
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
    if (!this.grupoSeleccionado) return;

    const idGrupo = this.grupoSeleccionado.id;
    const idCreador = this.idUsuario;

    this.grupoService.eliminarGrupo(idGrupo, idCreador).subscribe({
      next: () => {
        this.listarGruposConRol(this.idUsuario);
        this.cancelarEliminarGrupo();
      },
      error: (error) => {
        console.error('Error al eliminar grupo:', error);
      }
    });
  }

  mostrarActualizarGrupo = false;
  nombreGrupoEditar = '';
  fotoGrupoEditar: File | null = null;

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

  abrirActualizarContacto(contacto: any): void {
    console.log('CONTACTO SELECCIONADO:', contacto);
    console.log('ID DEL CONTACTO:', contacto.id);

    this.contactoSeleccionado = contacto;
    this.nombreContactoEditar = contacto.nombrePersonalizado || '';
    this.mostrarActualizarContacto = true;
    this.menuAbierto = null;
  }

  cancelarActualizarContacto(): void {
    this.mostrarActualizarContacto = false;
    this.nombreContactoEditar = '';
  }

  guardarCambiosContacto(): void {
    if (!this.nombreContactoEditar.trim()) {
      return;
    }

    this.contactoService.actualizarNombreContacto(this.idUsuario, this.contactoSeleccionado.idUsuarioContacto, this.nombreContactoEditar.trim()).subscribe({
      next: () => {
        this.mostrarActualizarContacto = false;
        this.nombreContactoEditar = '';
        this.listarContactos(this.idUsuario);
      },
      error: (error) => {
        console.error('Error al actualizar contacto:', error);
      }
    });
  }

  confirmarSalirGrupo(grupo: any): void {
    this.grupoParaSalir = grupo; this.mostrarConfirmacionSalir = true;
  }

  cancelarSalirGrupo(): void {
    this.mostrarConfirmacionSalir = false; this.grupoParaSalir = null;
  }

  salirDelGrupo(): void { if (!this.grupoParaSalir) return;

    const idGrupo = this.grupoParaSalir.id;

    this.grupoService.salirGrupo(idGrupo, this.idUsuario).subscribe({
      next: () => {
        this.grupos.update(lista =>
          lista.filter(g => g.id !== idGrupo)
        );

        this.cancelarSalirGrupo();
      },
      error: error => {
        console.error('Error al salir del grupo:', error);
      }
    });
  }

  actualizarContacto(id: number, nombre: string): void{
    if(!nombre.trim()){
      this.mostrar('Debe digitar el nuevo nombre que le desea colocar', 'error')
      return;
    }
    this.contactoService.actualizarNombreContacto(
      this.idUsuario,
      this.contactoSeleccionado.idUsuarioContacto,
      this.nombreContactoEditar.trim()
    ).subscribe({
      next: () => {
        this.mostrarActualizarContacto = false;
        this.nombreContactoEditar = '';
        this.listarContactos(this.idUsuario);
      },
      error: (error) => {
        console.error('Error al actualizar contacto:', error);
      }
    });

  }

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.cdr.markForCheck();

    // Evita que un temporizador anterior borre el mensaje nuevo
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