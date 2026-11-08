import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {NuevoContacto} from '../nuevo-contacto/nuevo-contacto';

@Injectable({
  providedIn: 'root'
})
export class ContactoService {
  private baseUrl = 'http://localhost:8080/contactos';

  constructor(private http: HttpClient) {
  }

  crearContacto(idUsuario: number, idUsuarioContacto: number, nombrePersonalizado: string): Observable<string> {
    return this.http.post(
      `${this.baseUrl}/crearContacto`,
      {
        idUsuario: idUsuario,
        idUsuarioContacto: idUsuarioContacto,
        nombrePersonalizado: nombrePersonalizado
      },
      {
        responseType: 'text'
      }
    );
  }
  eliminarContacto(idUsuario: number, idUsuarioContacto: number): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/eliminarContacto`,
      {
        idUsuario: idUsuario,
        idUsuarioContacto: idUsuarioContacto
      }
    );
  }
  listarContactosPorUsuario(idUsuario: number): Observable<NuevoContacto[]> {
    return this.http.get<NuevoContacto[]>(
      `${this.baseUrl}/listaContactos/${idUsuario}`
    );
  }

  actualizarNombreContacto(idUsuario: number, idUsuarioContacto: number, nombre: string): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/actualizarNombre/${idUsuario}/${idUsuarioContacto}`,
      null,
      { params: { nombre } }
    );
  }

}
