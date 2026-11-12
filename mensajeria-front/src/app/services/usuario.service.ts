import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Usuario} from '../model/usuario.model';

@Injectable({providedIn: 'root'})
export class UsuarioService {
  private apiUrl ='http://localhost:8080/usuarios';

  constructor(private http: HttpClient) { }

  eliminarUsuario(id: number){
    const params = new HttpParams()
      .set('id', id);

    return this.http.delete(`${this.apiUrl}/${id}/eliminarUsuario`);
  }

  actualizarUsuario(usuario: Usuario, foto?: File | null) {
    const fd = new FormData();
    fd.append('usuario', new Blob([JSON.stringify(usuario)], { type: 'application/json' }));
    if (foto) {
      fd.append('foto', foto);
    }
    return this.http.put(`${this.apiUrl}/actualizarUsuario`, fd, { responseType: 'text' });
  }

  obtenerUsuarioPorTelefono(telefono: string) {
    return this.http.get<any>(`${this.apiUrl}/obtener/${telefono}`);
  }
}
