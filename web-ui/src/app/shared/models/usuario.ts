import { Posicion } from './posicion';

export class Usuario {
  codigo: string; // Matricula
  nombre: string;
  contrasenha: string;
  nombreCompleto: string;
  usuarioRed: string;
  fechaCreacion: Date;
  posicionResponsableCodigo: string;
  rolId: number;
  procesoId: number;
  posicion: Posicion;
}
