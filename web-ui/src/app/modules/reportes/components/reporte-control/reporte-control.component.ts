import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Area } from 'src/app/shared/models/area';
import { Centro } from 'src/app/shared/models/centro';
import * as fileSaver from 'file-saver';
import { ReporteService } from 'src/app/shared/services/reporte.service';
import { NgSelectComponent } from '@ng-select/ng-select';
import { FormControl } from '@angular/forms';
import { Tipo } from 'src/app/shared/models/tipo';
import swal from 'sweetalert2';

@Component({
  selector: 'app-reporte-control',
  templateUrl: './reporte-control.component.html',
  styleUrls: ['./reporte-control.component.scss']
})
export class ReporteControlComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;

  @Input() areas: Area[];
  selectedAreas = [];

  @Input() centros: Centro[];
  selectedCentros = [];

  @Input() estados: Tipo[];
  selectedEstados = [];

  titulo = 'REPORTE DE CONTROL';

  constructor(private reporteService: ReporteService) { }

  ngOnInit() {
  }

  descargar() {
    if (this.selectedProceso == null || this.selectedAreas.length == 0 || this.selectedCentros.length == 0 || this.selectedEstados.length == 0) {
      swal.fire('Descargar Reporte', 'Por favor, seleccione los filtros.', 'error');
      return;
    }
    const filename = 'Reporte de control.xlsx';
    const filtro = {
      proceso: this.selectedProceso,
      areas: this.selectedAreas,
      centros: this.selectedCentros,
      estados: this.selectedEstados
    };

    this.reporteService.generarReporteControl(filtro).subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
    }, err => {
      console.log(err);
    });
  }
}
