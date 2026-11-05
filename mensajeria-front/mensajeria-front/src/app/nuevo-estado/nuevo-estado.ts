import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-nuevo-estado',
  standalone: false,
  templateUrl: './nuevo-estado.html',
  styleUrl: './nuevo-estado.css',
})
export class NuevoEstado {
  @Output() cerrar = new EventEmitter<void>();
}
