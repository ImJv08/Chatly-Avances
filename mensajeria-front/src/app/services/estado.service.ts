import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Estado} from '../model/estado.model';

@Injectable({ providedIn: 'root' })
export class EstadoService {
  private baseUrl = 'http://localhost:8080/estados';
  constructor(private http: HttpClient) {}

  crearEstado(idUsuario: number, descripcion: string, archivo: File) {
    const fd = new FormData();
    fd.append('estado', new Blob([JSON.stringify({ idUsuario, descripcion })], { type: 'application/json' }));
    fd.append('archivo', archivo);
    return this.http.post(`${this.baseUrl}/crearEstado`, fd, { responseType: 'text' });
  }

  listar(idUsuario: number) {
    return this.http.get<Estado[]>(`${this.baseUrl}/listaEstados/${idUsuario}`);
  }

  eliminar(idEstado: number) {
    return this.http.delete(`${this.baseUrl}/eliminarEstado/${idEstado}`);
  }
}
