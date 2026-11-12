import {Injectable, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Notificacion} from '../model/notificaciones.model';

@Injectable({
  providedIn: 'root'
})
export class NotificacionService {

  private baseUrl = 'http://localhost:8080/notificaciones';

  constructor(private http: HttpClient) {}

  noLeidas = signal(0);
  listarNotificaciones(idUsuario: number): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(`${this.baseUrl}/${idUsuario}`);
  }

  marcarLeida(id: number) {
    return this.http.put(`${this.baseUrl}/${id}/leer`, null);
  }

  contarNoLeidas(idUsuario: number) {
    return this.http.get<number>(`${this.baseUrl}/${idUsuario}/no-leidas`).pipe(
      tap(n => this.noLeidas.set(n))
    );
  }

  marcarTodasLeidas(idUsuario: number) {
    return this.http.put(`${this.baseUrl}/usuario/${idUsuario}/leer-todas`, null);
  }
}
