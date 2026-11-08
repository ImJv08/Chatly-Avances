import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Notificacion} from '../model/notificaciones.model';

@Injectable({
  providedIn: 'root'
})
export class NotificacionService {

  private baseUrl = 'http://localhost:8080/notificaciones';

  constructor(private http: HttpClient) {}

  listarNotificaciones(idUsuario: number): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(`${this.baseUrl}/${idUsuario}`);
  }
}
