import { LineaCanal } from '../models/linea-canal';
import { Encuesta } from '../models/encuesta';
import { Justificacion } from '../models/justificacion';

export const LINEA_CANAL_mock: any = [
    {
        linea: { id: 1, codigo: "LIN01", nombre: "Linea 1", fechaCreacion: "2019-08-01", porcentaje: 10 },
        lstCanales: [
            { id: 1, codigo: "CAN01", nombre: "Canal 1", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 2, codigo: "CAN02", nombre: "Canal 2", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 3, codigo: "CAN03", nombre: "Canal 3", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 4, codigo: "CAN04", nombre: "Canal 4", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 5, codigo: "CAN05", nombre: "Canal 5", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 6, codigo: "CAN06", nombre: "Canal 6", fechaCreacion: "2019-08-01", porcentaje: 10},
        ]
    },
    {
        linea: { id: 2, codigo: "LIN02", nombre: "Linea 2", fechaCreacion: "2019-08-01", porcentaje: 10 },
        lstCanales: [
            { id: 1, codigo: "CAN01", nombre: "Canal 1", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 2, codigo: "CAN02", nombre: "Canal 2", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 3, codigo: "CAN03", nombre: "Canal 3", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 4, codigo: "CAN04", nombre: "Canal 4", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 5, codigo: "CAN05", nombre: "Canal 5", fechaCreacion: "2019-08-01", porcentaje: 10}
        ]
    },
    {
        linea: { id: 3, codigo: "LIN03", nombre: "Linea 3", fechaCreacion: "2019-08-01", porcentaje: 10 },
        lstCanales: [
            { id: 1, codigo: "CAN01", nombre: "Canal 1", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 2, codigo: "CAN02", nombre: "Canal 2", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 3, codigo: "CAN03", nombre: "Canal 3", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 4, codigo: "CAN04", nombre: "Canal 4", fechaCreacion: "2019-08-01", porcentaje: 10}
        ]
    },
    {
        linea: { id: 4, codigo: "LIN04", nombre: "Linea 4", fechaCreacion: "2019-08-01", porcentaje: 10 },
        lstCanales: [
            { id: 1, codigo: "CAN01", nombre: "Canal 1", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 2, codigo: "CAN02", nombre: "Canal 2", fechaCreacion: "2019-08-01", porcentaje: 10},
            { id: 3, codigo: "CAN03", nombre: "Canal 3", fechaCreacion: "2019-08-01", porcentaje: 10}
        ]
    }
]
