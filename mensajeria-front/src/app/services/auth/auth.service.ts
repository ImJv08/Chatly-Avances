import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, map, Observable, tap, throwError} from 'rxjs';
import { Usuario } from '../../model/usuario.model';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth';
  currentUserLoginOn = new BehaviorSubject<boolean>(false);
  currentUserData = new BehaviorSubject<string>('');

  constructor(private http: HttpClient, private router: Router) {
    // Inicializa con el token guardado o cadena vacía
    this.currentUserData = new BehaviorSubject<string>(sessionStorage.getItem('token') || '');
  }

// Cuando el usuario hace login exitoso
  login(credentials: Usuario) {
    const loginPayload = {
      numeroTelefonico: credentials.numeroTelefonico,
      contrasenia: credentials.contrasenia
    };
    return this.http.post<any>(`${this.apiUrl}/login`, loginPayload).pipe(
      tap(userData => {
        console.log('Datos del login:', userData);

        sessionStorage.setItem('token', userData.token);
        sessionStorage.setItem('telefono', credentials.numeroTelefonico);
        sessionStorage.setItem('idUsuario', String(userData.id));

        this.currentUserData.next(userData.token);
        this.currentUserLoginOn.next(true);
      }),
      map((userData) => userData.token),
      catchError((error) => this.handleError(error))
    );

  }
//Cerrar sesion
  longout():void{
    sessionStorage.removeItem('token');
    localStorage.removeItem('idUsuario');
    this.currentUserLoginOn.next(false);
  }


  registrar(usuario: Usuario): Observable<string> {
    return this.http.post(`${this.apiUrl}/register`, usuario, { responseType: 'text' });
  }

  private handleError(error:any) {
    if(error.status === 0) {
      console.error('se ha producido un error', error.error)
    }else {
      console.error(`Backend retornó el código de estado ${error.status}`, error.error);
    }
    return throwError(() => new Error('Algo fallo, por favor intente nuevamente') )
  }

  get userData():Observable<String> {
    return this.currentUserData.asObservable();
  }

  get userToken():String {
    return this.currentUserData.value;
  }

  subirFoto(archivo: File) {
    const fd = new FormData();
    fd.append('archivo', archivo);
    return this.http.post(`${this.apiUrl}/foto`, fd, { responseType: 'text' });
  }

}
