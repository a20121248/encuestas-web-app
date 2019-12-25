import { Component, OnInit, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-mantenimientos',
  templateUrl: './mantenimientos.component.html',
  styleUrls: ['./mantenimientos.component.scss']
})
export class MantenimientosComponent implements OnInit, OnDestroy {
  titulo = 'MANTENIMIENTO';
  links = [['Mantenimiento de colaboradores', 'colaboradores'],
           ['Mantenimiento de posiciones', 'posiciones'],
           ['Mantenimiento de áreas', 'areas'],
           ['Mantenimiento de centros de costos', 'centros-de-costos'],
           ['Mantenimiento de líneas', 'lineas'],
           ['Mantenimiento de canales', 'canales'],
           ['Mantenimiento de productos', 'productos'],
           ['Mantenimiento de subcanales', 'subcanales']/*,
           ['Mantenimiento de perfiles', 'perfiles'],
           ['Mantenimiento de datos de la posición', 'posiciones']*/];
  procesos: Proceso[];
  selectedProceso: Proceso;
  subscribeProceso: Subscription;

  constructor(
    private titleService: Title,
    private procesoService: ProcesoService) {
      this.subscribeProceso = this.procesoService.findAll().subscribe(procesos => {
        this.procesos = procesos;
        this.selectedProceso = procesos[procesos.length - 1];
      });
    }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento');
  }

  ngOnDestroy(): void {
    this.subscribeProceso.unsubscribe();
  }
}
