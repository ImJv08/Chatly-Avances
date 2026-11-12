export interface Estado {
  id: number;
  idUsuario: number;
  nombreUsuario: string;
  tipo: 'IMAGEN' | 'VIDEO';
  contenidoUrl: string;
  descripcion?: string;
  fechaPublicacion: string;
  fechaExpiracion: string;
}
