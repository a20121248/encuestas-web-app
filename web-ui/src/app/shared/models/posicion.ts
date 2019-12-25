import { Area } from './area';
import { Centro } from './centro';
import { Perfil } from './perfil';

export class Posicion {
  codigo: string;
  nombre: string;
  fechaCreacion: Date;
  fechaActualizacion: Date;
  fechaEliminacion: Date;
  area: Area;
  centro: Centro;
  perfil: Perfil;
}
