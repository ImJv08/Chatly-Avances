import {ChangeDetectorRef, Component, EventEmitter, inject, OnInit, Output, signal} from '@angular/core';
import {catchError, forkJoin, map, of, switchMap} from 'rxjs';
import { UsuarioService } from '../services/usuario.service';
import {ContactoService} from '../services/nuevo-contacto.service';
import {GrupoService} from '../services/nuevo-grupo.service';
import {Grupo} from '../model/grupos.model';


@Component({
  selector: 'app-nuevo-grupo',
  standalone: false,
  templateUrl: './nuevo-grupo.html',
  styleUrl: './nuevo-grupo.css'
})
export class NuevoGrupo implements OnInit {
  @Output() cerrar = new EventEmitter<void>();
  @Output() grupoCreado = new EventEmitter<String>();


  message: string = "";
  messageType: 'success' | 'error' | null = null;
  idUsuario!: number;
  nombreGrupo: string = '';
  seleccionados: number[] = [];
  grupos = signal<any[]>([]);
  contactos = signal<any[]>([]);
  vistaPrevia: string | null = null;
  archivo: File | null = null;

  private cdr = inject(ChangeDetectorRef);


  constructor(
    private contactoService: ContactoService,
    private usuarioService: UsuarioService,
    private grupoService: GrupoService
  ) {
  }

  ngOnInit(): void {
    this.idUsuario = Number(sessionStorage.getItem('idUsuario'));

    if (!this.idUsuario) {
      console.error('No se encontró el id del usuario en la sesión');
      return;
    }

    this.listarContactos(this.idUsuario);
  }

  onFoto(e: Event) {
    const f = (e.target as HTMLInputElement).files?.[0];
    if (!f) return;
    if (!f.type.startsWith('image/') || f.size > 5 * 1024 * 1024) {
      this.mostrar('Elige una imagen de máximo 5 MB', 'error');
      return;
    }
    this.archivo = f;
    this.vistaPrevia = URL.createObjectURL(f);
    this.cdr.markForCheck();
  }

  listarContactos(idUsuario: number): void {
    this.contactoService.listarContactosPorUsuario(idUsuario).subscribe({
      next: (respuesta) => {
        this.contactos.set(respuesta);
      },
      error: (error) => {
        this.mostrar(error.error?.mensaje || error.error?.message || 'Error al listar contactos', 'error')
      }
    });
  }

  alternarSeleccion(idUsuarioContacto: number, marcado: boolean): void {
    if (marcado) {
      if (!this.seleccionados.includes(idUsuarioContacto)) {
        this.seleccionados.push(idUsuarioContacto);
      }
    } else {
      this.seleccionados = this.seleccionados.filter(
        id => id !== idUsuarioContacto
      );
    }
  }

  crearGrupo(): void {

    if (!this.nombreGrupo.trim()) {
      this.mostrar('El grupo necesita un nombre', 'error');
      return;
    }

    this.grupoService.crearGrupo(this.nombreGrupo, this.idUsuario, this.archivo).pipe(
      switchMap(() =>
        this.grupoService.listarGruposPorUsuario(this.idUsuario)
      ),
      switchMap(grupos => {
        const grupo = grupos.find(
          g => g.nombreGrupo === this.nombreGrupo.trim()
        );

        if (!grupo) {
          throw new Error('No se encontró el grupo creado');
        }

        console.log('Grupo encontrado:', grupo);
        console.log('ID del grupo:', grupo.id);
        console.log('ID del origen:', this.idUsuario);

        const altas = this.seleccionados.map(idMiembro => {
          console.log('ID del miembro:', idMiembro);

          return this.grupoService.agregarMiembro(
            grupo.id,
            idMiembro,
            this.idUsuario
          ).pipe(
            map(() => true),
            catchError(() => of(false))
          );
        });

        return altas.length ? forkJoin(altas) : of([] as boolean[]);
      })
    ).subscribe({
      next: (resultados: boolean[]) => {
        const fallidos = resultados.filter(ok => !ok).length;
        const aviso = fallidos
          ? `Grupo creado, pero no se pudo agregar a ${fallidos} integrante(s)`
          : '';

        this.mostrar('Grupo creado correctamente', 'success');
        this.grupoCreado.emit(aviso);
        this.cerrar.emit();
      },
      error: error => {
        this.mostrar(error.error?.mensaje || error.message || 'No se pudo crear el grupo', 'error');
      }
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

  private mostrar(msg: string, tipo: 'error' | 'success') {
    this.message = msg;
    this.messageType = tipo;
    this.archivo = null;
    this.cdr.markForCheck();

    setTimeout(() => {
      this.message = '';
      this.messageType = null;
      this.cdr.detectChanges();
    }, 5000);
  }

}
