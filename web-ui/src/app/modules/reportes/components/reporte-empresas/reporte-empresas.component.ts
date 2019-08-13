import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Area } from 'src/app/shared/models/area';
import { Centro } from 'src/app/shared/models/centro';

@Component({
  selector: 'app-reporte-empresas',
  templateUrl: './reporte-empresas.component.html',
  styleUrls: ['./reporte-empresas.component.css']
})
export class ReporteEmpresasComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() areas: Area[];
  @Input() centros: Centro[];
  selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];

  constructor() { }

  ngOnInit() {
  }

}
