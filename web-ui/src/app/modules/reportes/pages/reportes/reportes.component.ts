import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FileService } from 'src/app/shared/services/file.service';
import * as fileSaver from 'file-saver';
import { AreaService } from 'src/app/shared/services/area.service';
import { Area } from 'src/app/shared/models/area';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/proceso';
import { CentroService } from 'src/app/shared/services/centro.service';
import { Centro } from 'src/app/shared/models/centro';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements OnInit {
  procesos: Proceso[];
  areas: Area[];
  centros: Centro[];

  selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];

  titulo = 'Reporting';
  constructor(
    private titleService: Title,
    private fileService: FileService,
    private areaService: AreaService,
    private procesoService: ProcesoService,
    private centroService: CentroService) {
      this.procesoService.findAll().subscribe(procesos => {
        this.procesos = procesos;
      });
      this.areaService.findAll().subscribe(areas => {
        this.areas = areas;
      });
      this.centroService.findAll().subscribe(centros => {
        this.centros = centros;
      });
    }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Reporting');
  }

  descargar() {
    this.fileService.downloadFile().subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), 'Report.xlsx');
    }, err => {
      console.log(err);
    });
  }
}
