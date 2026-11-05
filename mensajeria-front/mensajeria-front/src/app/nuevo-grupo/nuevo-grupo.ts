import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-nuevo-grupo',
  standalone: false,
  templateUrl: './nuevo-grupo.html',
  styleUrl: './nuevo-grupo.css',
})
export class NuevoGrupo {
  @Output() cerrar = new EventEmitter<void>();
}
