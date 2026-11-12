import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bloqueo } from '../model/bloqueo.model';

@Injectable({
	providedIn: 'root'
})
export class BloqueoService {
	private baseUrl = 'http://localhost:8080/bloqueos';

	constructor(private http: HttpClient) { }

	bloquear(idUsuario: number, idUsuarioBloqueado: number): Observable<void> {
		return this.http.post<void>(`${this.baseUrl}/bloquear`, {
			idUsuario: idUsuario,
			idUsuarioBloqueado: idUsuarioBloqueado
		});
	}

	desbloquear(idUsuario: number, idUsuarioBloqueado: number): Observable<void> {
		return this.http.delete<void>(
			`${this.baseUrl}/desbloquear/${idUsuario}/${idUsuarioBloqueado}`
		);
	}

	listarBloqueados(idUsuario: number): Observable<Bloqueo[]> {
		return this.http.get<Bloqueo[]>(`${this.baseUrl}/listaBloqueados/${idUsuario}`);
	}

  listarQuienMeBloqueo(idUsuario: number): Observable<Bloqueo[]> {
    return this.http.get<Bloqueo[]>(
      `${this.baseUrl}/quienMeBloqueo/${idUsuario}`
    );
  }
}
