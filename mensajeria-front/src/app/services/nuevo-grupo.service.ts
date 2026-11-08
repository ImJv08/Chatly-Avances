import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Grupo} from '../model/grupos.model';

@Injectable({
  providedIn: 'root'
})
export class GrupoService {
  private baseUrl = 'http://localhost:8080/grupos';

  constructor(private http: HttpClient) {
  }

  crearGrupo(nombre: string, idCreador: number): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/crearGrupo`,
      {
        nombreGrupo: nombre,
        idCreador: idCreador
      }
    );
  }

  agregarMiembro(idGrupo: number, idUsuario: number, idOrigen: number): Observable<void> {

    return this.http.post<void>(
      `${this.baseUrl}/agregarMiembros/${idOrigen}`,
      {
        grupo: idGrupo,
        usuario: idUsuario
      }
    );
  }

  eliminarGrupo(idGrupo: number, idCreador: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/eliminarGrupo/${idGrupo}/${idCreador}`
    );
  }

  salirGrupo(idGrupo: number, idUsuario: number): Observable<void> {
    return this.http.delete<void>( `${this.baseUrl}/salirGrupo`,
    {
      body: {
      grupo: idGrupo,
      usuario: idUsuario
    }
    });
  }

  eliminarMiembroGrupo(idGrupo: number, idUsuario: number, idOrigen: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/eliminarMiembroGrupo/${idOrigen}`,
      {
        body: {
          idGrupo: idGrupo,
          idUsuario: idUsuario
        }
      }
    );
  }

  listarGruposPorUsuario(idUsuario: number): Observable<Grupo[]> {
    return this.http.get<Grupo[]>(
      `${this.baseUrl}/listaGrupos/${idUsuario}`
    );
  }

  listarGruposConRol(idUsuario: number) {
    return this.http.get<any[]>(
      `${this.baseUrl}/usuario/${idUsuario}/con-rol`
    );
  }

  actualizarGrupo(idCreador: number, grupo: any) {
    return this.http.put(
      `${this.baseUrl}/actualizarGrupo/${idCreador}`,
      grupo,
      { responseType: 'text' }
    );
  }


}
