import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AreaService } from 'src/app/shared/services/area.service';
import { Area } from 'src/app/shared/models/area';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/proceso';
import { CentroService } from 'src/app/shared/services/centro.service';
import { Centro } from 'src/app/shared/models/centro';
import { AuthService } from 'src/app/shared/services/auth.service';
import { NgSelectConfig } from '@ng-select/ng-select';
import { Tipo } from 'src/app/shared/models/tipo';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.scss']
})
export class ReportesComponent implements OnInit {
  procesos: Proceso[];
  areas: Area[];
  centros: Centro[];
  estados: Tipo[];

  selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];

  titulo = 'Reporting';
  constructor(
    public authService: AuthService,
    private titleService: Title,
    private areaService: AreaService,
    private procesoService: ProcesoService,
    private centroService: CentroService,
    private config: NgSelectConfig) {
      this.config.notFoundText = 'No se encontraron elementos.';
      this.selectedProceso = this.authService.proceso;
      this.procesoService.findAll().subscribe(procesos => {
        this.procesos = procesos;
      });
      this.areaService.findAll().subscribe(areas => {
        this.areas = areas;
      });
      this.centroService.findAll().subscribe(centros => {
        this.centros = centros;
      });
      this.estados = new Array();
      this.estados.push(new Tipo(2, 'COMPLETADA', 'TODOS'));
      this.estados.push(new Tipo(1, 'INICIADA', 'TODOS'));
      this.estados.push(new Tipo(0, 'NO INICIADA', 'TODOS'));
    }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Reporting');
  }
}
