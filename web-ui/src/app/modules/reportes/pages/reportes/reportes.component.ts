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
    }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Reporting');
  }
}
