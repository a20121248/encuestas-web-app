import { Injectable } from '@angular/core';
import { Centro } from '../models/centro';
import { of, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CentroService {

  constructor() { }

  lstCentros: Centro[] = [
    {id: 1, codigo: '12.12.31',
    nombre: 'FIN - EQUIPO DE CONTABILIDAD TRIBUTARIA', abreviatura: 'FINANZAS', nivel: 1, fechaCreacion: '12-12-2019'},
    {id: 2, codigo: '12.12.31',
    nombre: 'GERENCIA DE CUMPLIMIENTO Y ÉTICA', abreviatura: 'FINANZAS', nivel: 2, fechaCreacion: '12-12-2019'},
    {id: 3, codigo: '12.12.31',
     nombre: 'EQUIPO DE CUMPLIMIENTO', abreviatura: 'FINANZAS', nivel: 3, fechaCreacion: '12-12-2019'},
    {id: 4, codigo: '12.12.31',
    nombre: 'GERENCIA DE AUDITORÍA INTERNA', abreviatura: 'FINANZAS', nivel: 4, fechaCreacion: '12-12-2019'},
    {id: 5, codigo: '12.12.31',
    nombre: 'EQUIPO DE AUDITORÍA DE PROCESOS SEGUROS', abreviatura: 'FINANZAS', nivel: 5, fechaCreacion: '12-12-2019'}
];

  getCentros(): Observable<Centro[]> {
    return of(this.lstCentros);
  }
}
