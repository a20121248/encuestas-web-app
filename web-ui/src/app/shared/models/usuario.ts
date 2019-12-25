import { Posicion } from './posicion';

export class Usuario {
  codigo: string; // Matricula
  usuarioVida: string;
  usuarioGenerales: string;
  contrasenha: string;
  nombreCompleto: string;
  usuarioRed: string;
  fechaCreacion: Date;
  fechaActualizacion: Date;
  fechaEliminacion: Date;
  posicionResponsableCodigo: string;
  roles: string[];
  procesoId: number;
  posicion: Posicion;
  estado: boolean;
  // estado: refleja el estado global de las encuestas del usuario
  // Logica1 => Observar los estados de completitud por usuario de las encuestas
  // (empresa, (centro,lineas(producto-canal,producto-subcanal), lineas-canales(producto-canal,producto-subcanal)), eps)
  // Logica2* => Observar el estado de completitud por usuario de su encuesta empresa.
  // (convendría agregar un campo adicional a la tabla encuestas
  // estado de la encuesta (que ya esta) y
  // estado de las encuestas hijo directo (nueva columna) cuando esten completos sus hijos cambia a true <=== y este dato enviarías en el json)
}
