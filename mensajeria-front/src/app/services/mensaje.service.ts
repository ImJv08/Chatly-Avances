import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Mensaje, MensajeDTO} from '../model/mensaje.model';

@Injectable({ providedIn: 'root' })
export class MensajeService {
  private api = 'http://localhost:8080/mensajes';

  constructor(private http: HttpClient) {}

  enviar(dto: MensajeDTO): Observable<string> {
    return this.http.post(`${this.api}/enviar`, dto, { responseType: 'text' });
  }

  conversacion(idUsuario: number, idContacto: number): Observable<Mensaje[]> {
    return this.http.get<Mensaje[]>(`${this.api}/conversacion/${idUsuario}/${idContacto}`);
  }

  deGrupo(idGrupo: number): Observable<Mensaje[]> {
    return this.http.get<Mensaje[]>(`${this.api}/grupo/${idGrupo}`);
  }

  marcarLeidos(idUsuario: number, idContacto: number) {
    return this.http.put(`${this.api}/leer/${idUsuario}/${idContacto}`, null);
  }

  subirArchivo(archivo: File): Observable<{ url: string; nombreArchivo: string; tipo: string }> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    return this.http.post<{ url: string; nombreArchivo: string; tipo: string }>(
      `${this.api}/subir-archivo`, formData
    );
  }

  noLeidos(idUsuario: number): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/no-leidos/${idUsuario}`);
  }
  eliminar(idMensaje: string, idUsuario: number): Observable<string> {
    return this.http.delete(`${this.api}/eliminar/${idMensaje}/${idUsuario}`, { responseType: 'text' });
  }

  editar(idMensaje: string, idUsuario: number, texto: string) {
    return this.http.put(`${this.api}/editar/${idMensaje}/${idUsuario}`, null,
      { params: { texto }, responseType: 'text' });
  }
}
