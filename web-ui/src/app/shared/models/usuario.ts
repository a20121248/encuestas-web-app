import { Posicion } from './posicion';

export class Usuario {
  codigo: string; // Matricula
  nombre: string;
  contrasenha: string;
  nombreCompleto: string;
  posicionCodigo:string;
  usuarioRed: string;
  fechaCreacion: Date;
  tipoEncuestaId: number;
  posicionResponsableCodigo: string;
  rolId: number;
  procesoId: number;
  posicion: Posicion;
}
