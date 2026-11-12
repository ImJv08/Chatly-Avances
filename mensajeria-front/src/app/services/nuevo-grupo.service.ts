import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Grupo} from '../model/grupos.model';
import {Contacto} from '../model/contacto.model';

@Injectable({
  providedIn: 'root'
})
export class GrupoService {
  private baseUrl = 'http://localhost:8080/grupos';

  constructor(private http: HttpClient) {
  }

  crearGrupo(nombreGrupo: string, idCreador: number, foto?: File | null) {
    const fd = new FormData();
    const grupo = { nombreGrupo, idCreador };
    fd.append('grupo', new Blob([JSON.stringify(grupo)], { type: 'application/json' }));
    if (foto) {
      fd.append('foto', foto);
    }
    return this.http.post(`${this.baseUrl}/crearGrupo`, fd, { responseType: 'text' });
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
    return this.http.delete<void>(`${this.baseUrl}/eliminarMiembroGrupo/${idOrigen}`, {
      body: {
        grupo: idGrupo,
        usuario: idUsuario
      }
    });
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

  actualizarGrupo(idCreador: number, grupo: any, foto?: File | null) {
   const fd = new FormData();
   fd.append('grupo', new Blob([JSON.stringify(grupo)], { type: 'application/json' }));
   if (foto) {
     fd.append('foto', foto);
   }
    return this.http.put(
      `${this.baseUrl}/actualizarGrupo/${idCreador}`, fd, { responseType: 'text' });
  }

  listarMiembrosGrupo(idGrupo: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${idGrupo}/miembros`);
  }

  cambiarRolAdmin(idGrupo: number,  idUsuario: number, idOrigen: number): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/cambiarRolAdmin/${idOrigen}`,
      {
        grupo: idGrupo,
        usuario: idUsuario
      }
    );
  }
}
