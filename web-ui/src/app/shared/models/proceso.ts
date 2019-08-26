import { Usuario } from './usuario';

export class Proceso {
  id: number;
  codigo: string;
  nombre: string;
  usuario: Usuario;
  fechaCierre: Date;
  fechaCreacion: Date;
  fechaActualizacion: Date;
}
