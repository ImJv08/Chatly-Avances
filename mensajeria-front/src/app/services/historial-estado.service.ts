import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HistorialEstado} from '../model/historial-estado.model';

@Injectable({ providedIn: 'root' })
export class HistorialEstadoService {
  private apiUrl = 'http://localhost:8080/historial-estados';
  constructor(private http: HttpClient) {}

  listar(idUsuario: number) {
    return this.http.get<HistorialEstado[]>(`${this.apiUrl}/${idUsuario}`);
  }
}
