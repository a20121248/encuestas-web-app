import { Tipo } from './tipo';

export class Centro {
  id: number;
  codigo: string;
  nombre: string;
  nivel: number;
  grupo: string;
  tipoId: number;
  tipo: Tipo;
  empresaId: number;
  fechaCreacion: Date;
  fechaActualizacion: Date;
  fechaEliminacion: Date;
  porcentaje: number;
  procesoId: number;
  agrupador: string;
}
