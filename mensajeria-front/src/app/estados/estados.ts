import {ChangeDetectorRef, Component, inject, OnInit, signal} from '@angular/core';
import { Router } from '@angular/router';
import {EstadoService} from '../services/estado.service';
import {Estado} from '../model/estado.model';


@Component({
  selector: 'app-estados',
  standalone: false,
  templateUrl: './estados.html',
  styleUrl: './estados.css'
})
export class Estados {
  modal: 'estados' | null = null;
  estados = signal<Estado[]>([]);
  estadoAbierto: Estado | null = null;
  idUsuario = Number(sessionStorage.getItem('idUsuario'));
  private estadoService = inject(EstadoService);

  ngOnInit() {
    this.

    cargar();
  }

  cargar() {
    this.estadoService.listar(this.idUsuario).subscribe(e => this.estados.set(e));
  }

  eliminar(e: Estado) {
    this.estadoService.eliminar(e.id).subscribe(() => { this.estadoAbierto = null; this.cargar(); });
  }

  haceCuanto(fecha: string): string {
    const seg = Math.floor((Date.now() - new Date(fecha).getTime()) / 1000);
    if (seg < 60) return 'Hace un momento';
    const min = Math.floor(seg / 60);
    if (min < 60) return `Hace ${min} min`;
    const h = Math.floor(min / 60);
    if (h < 24) return `Hace ${h} h`;
    return `Hace ${Math.floor(h / 24)} d`;
  }
}
