import { Tipo } from './tipo';
import { Centro } from './centro';
import { ObjetoObjetos } from './objeto-objetos';

export class Perfil {
  id: number;
  nombre: string;
  descripcion: string;
  tipo: Tipo;
  lstCentros: Centro[];
  lstLineasCanales: ObjetoObjetos[];
  fechaCreacion: Date;
  fechaActualizacion: Date;
}
