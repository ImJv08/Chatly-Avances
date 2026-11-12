export interface Mensaje {
  id: string;
  idEmisor: number;
  idReceptor: number | null;
  idGrupo: number | null;
  tipo: string;
  texto: string | null;
  contenidoUrl: string | null;
  nombreArchivo: string | null;
  fecha: string;
  leido: boolean;
  editado?: boolean;
}

export interface MensajeDTO {
  idEmisor: number;
  idReceptor?: number | null;
  idGrupo?: number | null;
  tipo: string;
  texto?: string | null;
  contenidoUrl?: string | null;
  nombreArchivo?: string | null;
}

