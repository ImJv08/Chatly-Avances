import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-bloqueados',   // app-nuevo-grupo, app-nuevo-estado, app-bloqueados
  standalone: false,
  templateUrl: './bloqueados.html',
  styleUrl: './bloqueados.css',
})
export class Bloqueados {
  @Output() cerrar = new EventEmitter<void>();
}
