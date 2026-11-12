export interface Contacto {
  id?: number;
  idUsuario?: number;
  idUsuarioContacto: number;
  nombrePersonalizado: string;
  telefono: string;
  urlFotoPerfil?: string;
  fechaAgregado?: Date;
}
