import {ChangeDetectorRef, Component, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {ContactoService} from '../services/nuevo-contacto.service';
import {UsuarioService} from '../services/usuario.service';
import {GrupoService} from '../services/nuevo-grupo.service';
import {BloqueoService} from '../services/bloqueo.service';
import {Contacto} from '../model/contacto.model';
import {MensajeService} from '../services/mensaje.service';
import {Mensaje} from '../model/mensaje.model';

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
  textoBusqueda: string = '';
  contactosFiltrados = signal<any[]>([]);
  gruposFiltrados = signal<any[]>([]);
  cargando = true;

  mostrarConfirmacion = false;
  contactoSeleccionado: any = null;
  mostrarConfirmacionGrupo = false;
  grupoSeleccionado: any = null;
  contactoChatSeleccionado: any = null;
  nombreUsuario: string = sessionStorage.getItem('nombreUsuario') || '';
  mostrarConfirmacionSalir = false;
  grupoParaSalir: any = null;message: string = "";
  messageType: 'success' | 'error' | null = null;
  mostrarActualizarContacto = false;
  nombreContactoEditar = '';
  seleccion: 'contacto' | 'grupo' | null = null;
  bloqueadosIds = signal<number[]>([]);
  chatActivo = signal<{ tipo: 'CONTACTO' | 'GRUPO'; id: number; nombre: string } | null>(null);
  mensajes = signal<Mensaje[]>([]);
  contactoActivo = signal<any | null>(null);
  private intervalo?: any;
  private intervaloContactos?: any;
  error = signal('');
  grupoActivo = signal<any | null>(null);
  noLeidos = signal<Record<string, number>>({});
  aviso = signal('');
  private primeraCargaNoLeidos = true;
  private intervaloNoLeidos?: any;

  private cdr = inject(ChangeDetectorRef);

  constructor(private contactoService: ContactoService, private usuarioService: UsuarioService,  private grupoService: GrupoService,
              private bloqueoService: BloqueoService, private mensajeService: MensajeService) {}


  ngOnInit(): void {
    const telefono = sessionStorage.getItem('telefono');
    this.intervaloNoLeidos = setInterval(() => this.cargarNoLeidos(), 3000);
    if (!telefono) {
      this.cargando = false;
      console.error('No se encontró el teléfono en la sesión');
      return;
    }

    this.usuarioService.obtenerUsuarioPorTelefono(telefono).subscribe({
      next: (usuario) => {
        this.idUsuario = usuario.id;

        this.listarContactos(this.idUsuario);
        this.listarGruposConRol(this.idUsuario);
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error);
      }
    });
    this.usuarioService.obtenerUsuarioPorTelefono(telefono).subscribe({
      next: (usuario) => {
        this.idUsuario = usuario.id;

        this.listarContactos(this.idUsuario);
        this.listarGruposConRol(this.idUsuario);

        this.intervalo = setInterval(() => this.cargar(), 3000);
        this.intervaloContactos = setInterval(() => this.listarContactos(this.idUsuario), 10000);  // <- nueva
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error);
      }
    });
  }

  listarGruposConRol(idUsuario: number): void {
    this.grupoService.listarGruposConRol(idUsuario).subscribe({
      next: (grupos) => {
        this.grupos.set(grupos);

        const abierto = this.grupoActivo();
        if (abierto) {
          this.grupoActivo.set(grupos.find(g => g.id === abierto.id) ?? null);
        }

        this.buscarChats();
        this.cargando = false;
      },
      error: (error) => {
        this.cargando = false;
        console.error('Error al listar grupos con rol:', error);
      }
    });
  }

  listarContactos(idUsuario: number): void {
    this.contactoService.listarContactosPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        console.log('CONTACTOS RECIBIDOS:', respuesta);
        this.contactos.set(respuesta);
        this.buscarChats();
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

  cerrarBloqueados(): void {
    this.verBloqueados = false;
    this.cargarBloqueados();
  }

  cargarBloqueados(): void {
    this.bloqueoService.listarBloqueados(this.idUsuario).subscribe({
      next: (lista) => {
        this.bloqueadosIds.set(lista.map(b => b.idUsuarioBloqueado));
      },
      error: (error) => console.error('Error al cargar bloqueados', error)
    });
  }


  menuAbierto: string | null = null;

  toggleMenu(id: string, event: MouseEvent): void {
    event.stopPropagation();
    this.menuAbierto = this.menuAbierto === id ? null : id;
  }

  estaBloqueado(contacto: any): boolean {
    return this.bloqueadosIds().includes(contacto.idUsuarioContacto);
  }


  buscarChats(): void {
    const texto = this.textoBusqueda.trim().toLowerCase();

    if (!texto) {
      this.contactosFiltrados.set(this.contactos());
      this.gruposFiltrados.set(this.grupos());
      return;
    }

    const contactosEncontrados = this.contactos().filter(contacto =>
      (contacto.nombrePersonalizado || '').toLowerCase().includes(texto) ||
      (contacto.telefono || '').toLowerCase().includes(texto)
    );

    const gruposEncontrados = this.grupos().filter(grupo =>
      (grupo.nombreGrupo || grupo.nombre || '').toLowerCase().includes(texto)
    );

    this.contactosFiltrados.set(contactosEncontrados);
    this.gruposFiltrados.set(gruposEncontrados);
  }

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.cdr.markForCheck();
  }




  abrirContacto(c: any) {
    this.grupoActivo.set(null);
    this.contactoActivo.set(c);
    this.chatActivo.set({
      tipo: 'CONTACTO',
      id: c.idUsuarioContacto,
      nombre: c.nombrePersonalizado || c.telefono
    });
    this.cargar();
  }

  cargar() {
    const chat = this.chatActivo();
    if (!chat || !this.idUsuario) return;
    if (!this.contactoActivo() && !this.grupoActivo()) return;

    const peticion = chat.tipo === 'GRUPO'
      ? this.mensajeService.deGrupo(chat.id)
      : this.mensajeService.conversacion(this.idUsuario, chat.id);

    peticion.subscribe(m => {
      this.mensajes.set(m);

      const hayPendientes = m.some(x => x.idEmisor !== this.idUsuario && !x.leido);
      if (chat.tipo === 'CONTACTO' && hayPendientes) {
        this.mensajeService.marcarLeidos(this.idUsuario, chat.id).subscribe();
      }
    });
  }

  enviar(texto: string) {
    const chat = this.chatActivo();
    console.log('enviar →', { chat, idUsuario: this.idUsuario, texto });
    if (!chat || !texto.trim()) return;

    this.mensajeService.enviar({
      idEmisor: this.idUsuario,
      idReceptor: chat.tipo === 'CONTACTO' ? chat.id : null,
      idGrupo: chat.tipo === 'GRUPO' ? chat.id : null,
      tipo: 'TEXTO',
      texto: texto.trim()
    }).subscribe({
      next: () => { this.error.set(''); this.cargar(); },
      error: (e) => this.error.set(e.error)
    });
  }

  ngOnDestroy() {
    clearInterval(this.intervalo);
    clearInterval(this.intervaloContactos);
    clearInterval(this.intervaloNoLeidos);
  }

  abrirGrupo(g: any) {
    this.contactoActivo.set(null);
    this.grupoActivo.set(g);
    this.chatActivo.set({ tipo: 'GRUPO', id: g.id, nombre: g.nombreGrupo || g.nombre });
    this.mensajes.set([]);
    this.cargar();
  }

  cerrarChat() {
    this.contactoActivo.set(null);
    this.grupoActivo.set(null);
    this.chatActivo.set(null);
    this.mensajes.set([]);
  }

  enviarArchivo(archivo: File) {
    const chat = this.chatActivo();
    if (!chat) return;

    if (archivo.size > 5 * 1024 * 1024) {
      this.error.set('El archivo supera los 5 MB');
      return;
    }
    if (archivo.size > 5 * 1024 * 1024) {
      this.error.set('La imagen supera los 5 MB');
      return;
    }

    this.mensajeService.subirArchivo(archivo).subscribe({
      next: (r) => {
        this.mensajeService.enviar({
          idEmisor: this.idUsuario,
          idReceptor: chat.tipo === 'CONTACTO' ? chat.id : null,
          idGrupo: chat.tipo === 'GRUPO' ? chat.id : null,
          tipo: r.tipo,
          contenidoUrl: r.url,
          nombreArchivo: r.nombreArchivo
        }).subscribe({
          next: () => { this.error.set(''); this.cargar(); },
          error: (e) => this.error.set(e.error)
        });
      },
      error: (e) => this.error.set(typeof e.error === 'string' ? e.error : 'No se pudo subir la imagen')
    });
  }
  cargarNoLeidos() {
    if (!this.idUsuario) return;

    this.mensajeService.noLeidos(this.idUsuario).subscribe(n => {
      const previo = this.noLeidos();
      const abierto = this.chatActivo();

      if (!this.primeraCargaNoLeidos) {
        for (const id of Object.keys(n)) {
          const esChatAbierto = abierto?.tipo === 'CONTACTO' && String(abierto.id) === id;
          if (!esChatAbierto && n[id] > (previo[id] ?? 0)) {
            this.avisar(id);
          }
        }
      }
      this.primeraCargaNoLeidos = false;
      this.noLeidos.set(n);
    });
  }

  private avisar(idEmisor: string) {
    const contacto = this.contactos().find(c => String(c.idUsuarioContacto) === idEmisor);
    const nombre = contacto ? (contacto.nombrePersonalizado || contacto.telefono) : 'Alguien';

    this.aviso.set(`💬 Nuevo mensaje de ${nombre}`);
    setTimeout(() => this.aviso.set(''), 4000);
  }

  eliminarMensaje(id: string) {
    this.mensajeService.eliminar(id, this.idUsuario).subscribe({
      next: () => { this.error.set(''); this.cargar(); },
      error: (e) => this.error.set(e.error)
    });
  }
}



