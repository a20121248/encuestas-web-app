import { Area } from './area';
import { Centro } from './centro';

export class Posicion {
  codigo: string;
  nombre: string;
  fechaCreacion: Date;
  fechaActualizacion: Date;
  area: Area;
  centro: Centro;
}
