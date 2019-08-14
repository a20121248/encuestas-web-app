import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Area } from 'src/app/shared/models/area';
import { Centro } from 'src/app/shared/models/centro';
import * as fileSaver from 'file-saver';
import { ReporteService } from 'src/app/shared/services/reporte.service';

@Component({
  selector: 'app-reporte-control',
  templateUrl: './reporte-control.component.html',
  styleUrls: ['./reporte-control.component.css']
})
export class ReporteControlComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() areas: Area[];
  @Input() centros: Centro[];
  @Input() selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];
  titulo = 'REPORTE DE CONTROL';

  constructor(private reporteService: ReporteService) { }

  ngOnInit() {
  }

  descargar() {
    const filename = 'Reporte Control.xlsx';
    const filtro = {
      proceso: this.selectedProceso,
      areas: this.selectedAreas,
      centros: this.selectedCentros
    };
    this.reporteService.generarReporteControl(filtro).subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
    }, err => {
      console.log(err);
    });
  }
}
