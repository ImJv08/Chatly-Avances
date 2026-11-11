import {ChangeDetectorRef, Component, EventEmitter, inject, Output} from '@angular/core';
import {ContactoService} from '../services/nuevo-contacto.service';
import {UsuarioService} from '../services/usuario.service';
import {Contacto} from '../model/contacto.model';

@Component({
  selector: 'app-nuevo-contacto',
  standalone: false,
  templateUrl: './nuevo-contacto.html',
  styleUrl: './nuevo-contacto.css',
})
export class NuevoContacto {
  @Output() cerrar = new EventEmitter<void>();
  @Output() contactoCreado = new EventEmitter<void>();

  message: string = "";
  messageType: 'success' | 'error' | null = null;
  idUsuario!: number;
  nombre: string = '';
  telefono: string = '';
  fotoUrl: String = '';


  constructor(private contactoService: ContactoService, private usuarioService:UsuarioService) {
  }
  contactos: Contacto[] = [];

  private cdr = inject(ChangeDetectorRef);


  ngOnInit(): void {
    const telefono = sessionStorage.getItem('telefono');

    if (!telefono) {
      console.error('No se encontró el teléfono de la sesión');
      return;
    }

    this.usuarioService.obtenerUsuarioPorTelefono(telefono).subscribe({
      next: (usuario) => {
        this.idUsuario = usuario.id;
        console.log('ID del usuario conectado:', this.idUsuario);
      },
      error: (error) => {
        console.error('No se pudo obtener el usuario', error);
      }
    });
  }


  crearContacto(idUsuario: number): void {

    if(!this.telefono.trim()){
      this.mostrar('Debe digitar el número de telefono de su nuevo contacto', 'error')
      return;
    }
    console.log('Mi ID:', idUsuario);
    console.log('Botón presionado', idUsuario, this.telefono, this.nombre);

    this.usuarioService.obtenerUsuarioPorTelefono(this.telefono).subscribe({
      next: (usuario) => {
        console.log('ID del contacto:', usuario.id);

        this.contactoService.crearContacto(
          idUsuario,
          usuario.id,
          this.nombre
        ).subscribe({
          next: () => {
            console.log('Contacto creado correctamente');
            this.mostrar('Contacto creado correctamente', 'success');
            this.contactoCreado.emit();
            this.cerrar.emit();
          },
          error: (error) => {
            console.error('Error al crear contacto:', error);
            this.mostrar('Error al crear el contacto', 'error');
          }
        });
      },
      error: () => {
        this.mostrar(
          'No existe un usuario con ese número de teléfono',
          'error'
        );
      }
    });
  }


  eliminarContacto(idUsuario: number, idUsuarioContacto: number): void {
    this.contactoService.eliminarContacto(idUsuario, idUsuarioContacto).subscribe({
      next: () => {
        this.listarContactos(idUsuario);
      },
      error: (error) => {
        this.mostrar('Ha sucedido un error al intentar eliminar el usuario', 'error')
      }
    });
  }

  listarContactos(idUsuario: number): void {
    this.contactoService.listarContactosPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        this.contactos = respuesta;
      },
      error: (error) => {
        this.mostrar('Ha sucedido un error al intentar listar los usuarios', 'error')
      }
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


}
