export class Linea {
    id: number;
    codigo: string;
    nombre: string;
    fechaCreacion: Date;
    porcentaje: number;
    estado: boolean;
    // estado: refleja el estado de sus encuestas hijos. 
    // Logica => Observar el estado de completitud por usuario de sus encuestas matrices(producto-canal,producto-subcanal).
    // EPS => revisar si se completo el form-eps.
    // SEGUROS => presenta 3 tipos de hijos
    // * centros => revisar si form-centros se ha llenado.
    // * lineas => revisar si todas las lineas y sus encuestas hijas han sido completadas.
    // * linea-canal => revisar si todas las combinaciones linea-canal y sus encuestas hijas han sido completadas.
}