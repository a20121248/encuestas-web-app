import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Centro } from 'src/app/shared/models/centro';
import { Area } from 'src/app/shared/models/area';

@Component({
  selector: 'app-reporte-control',
  templateUrl: './reporte-control.component.html',
  styleUrls: ['./reporte-control.component.css']
})
export class ReporteControlComponent implements OnInit {
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
