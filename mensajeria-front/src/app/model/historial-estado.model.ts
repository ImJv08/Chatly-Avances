export interface HistorialEstado {
  id: number;
  idEstado: number | null;
  idUsuario: number;
  tipo: 'IMAGEN' | 'VIDEO';
  contenidoUrl: string;
  descripcion?: string;
  fechaPublicacion: string;
  fechaExpiracion: string;
  fechaEliminacion: string | null;
}
