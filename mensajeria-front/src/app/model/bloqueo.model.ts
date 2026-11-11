export interface Bloqueo {
  id: number;
  idUsuario: number;
  idUsuarioBloqueado: number;
  nombreBloqueado: string;
  numeroTelefonicoBloqueado: string;
  fotoPerfilUrlBloqueado: string | null;
  fecha: string;
}