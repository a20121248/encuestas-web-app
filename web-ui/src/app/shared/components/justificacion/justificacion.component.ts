import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';

export interface Justificacion {
  nombre: string;
  id: number;
}

@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.css']
})
export class JustificacionComponent implements OnInit {
  
  justificacionControl = new FormControl('', [Validators.required]);
  selectFormControl = new FormControl('', Validators.required);
  lstJustificaciones: Justificacion[] = [
    {nombre: 'Calculado en base a HH', id: 1},
    {nombre: 'Calculado en base a procesos auditados', id: 2},
    {nombre: 'Calculado en base a transacciones', id: 3},
    {nombre: 'Calculado en base a esfuerzo estimado', id: 4},
    {nombre: 'Otros', id: 5}
  ];
  constructor() { }

  ngOnInit() {
  }
}
