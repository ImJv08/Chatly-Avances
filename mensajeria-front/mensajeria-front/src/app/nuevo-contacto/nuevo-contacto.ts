import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-nuevo-contacto',
  standalone: false,
  templateUrl: './nuevo-contacto.html',
  styleUrl: './nuevo-contacto.css',
})
export class NuevoContacto {
  @Output() cerrar = new EventEmitter<void>();
}
