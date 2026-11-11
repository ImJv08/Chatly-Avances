import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {NuevoContacto} from '../nuevo-contacto/nuevo-contacto';
import {Contacto} from '../model/contacto.model';

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

  actualizarNombreContacto(idUsuario: number, idUsuarioContacto: number, nombre: string): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/actualizarNombre/${idUsuario}/${idUsuarioContacto}`,
      null,
      { params: { nombre } }
    );
  }

  listarContactosPorUsuario(idUsuario: number): Observable<Contacto[]> {
    return this.http.get<Contacto[]>(
      `${this.baseUrl}/listaContactos/${idUsuario}`
    );
  }

  buscarContactosPorNombreOTelefono(idUsuario: number, texto: string): Observable<Contacto[]> {
    const params = new HttpParams().set('texto', texto);

    return this.http.get<Contacto[]>(
      `${this.baseUrl}/buscarPorNombreOTelefono/${idUsuario}`,
      { params }
    );
  }

}
