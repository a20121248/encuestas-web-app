import { Component, OnInit, Input } from '@angular/core';
import { Centro } from 'src/app/shared/models/centro';
import { Proceso } from 'src/app/shared/models/proceso';
import { Area } from 'src/app/shared/models/area';

@Component({
  selector: 'app-reporte-consolidado',
  templateUrl: './reporte-consolidado.component.html',
  styleUrls: ['./reporte-consolidado.component.css']
})
export class ReporteConsolidadoComponent implements OnInit {
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
