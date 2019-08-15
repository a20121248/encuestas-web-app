import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Area } from 'src/app/shared/models/area';
import { Centro } from 'src/app/shared/models/centro';
import * as fileSaver from 'file-saver';
import { ReporteService } from 'src/app/shared/services/reporte.service';

@Component({
  selector: 'app-reporte-empresas',
  templateUrl: './reporte-empresas.component.html',
  styleUrls: ['./reporte-empresas.component.css']
})
export class ReporteEmpresasComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() areas: Area[];
  @Input() centros: Centro[];
  @Input() selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];
  titulo = 'REPORTE DE EMPRESAS';

  constructor(private reporteService: ReporteService) { }

  ngOnInit() {
  }

  descargar() {
    const filename = 'Reporte Control.xlsx';
    const filtro = {
      proceso: this.selectedProceso,
      areas: this.selectedAreas
    };
    this.reporteService.generarReporteEmpresas(filtro).subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
    }, err => {
      console.log(err);
    });
  }
}
