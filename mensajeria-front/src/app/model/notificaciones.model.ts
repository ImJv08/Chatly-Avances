export interface Notificacion {
  id: number;
  idUsuario: number;
  idUsuarioOrigen: number;
  leido: boolean;
  tipo: string;
  fecha: string;
}
