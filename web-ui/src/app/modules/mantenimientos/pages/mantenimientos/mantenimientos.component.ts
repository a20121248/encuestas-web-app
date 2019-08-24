import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-mantenimientos',
  templateUrl: './mantenimientos.component.html',
  styleUrls: ['./mantenimientos.component.css']
})
export class MantenimientosComponent implements OnInit {
  titulo = 'Módulo de Mantenimiento';
  links = [['Mantenimiento de usuarios', 'usuarios'],
           ['Mantenimiento de posiciones', 'posiciones'],
           ['Mantenimiento de áreas', 'areas'],
           ['Mantenimiento de centros de costos', 'centros-de-costos'],
           ['Mantenimiento de líneas', 'lineas'],
           ['Mantenimiento de canales', 'canales'],
           ['Mantenimiento de productos', 'productos'],
           ['Mantenimiento de subcanales', 'subcanales'],
           ['Mantenimiento de perfiles', 'perfiles'],
           ['Mantenimiento de datos de la posición', 'posiciones']];
  procesos: Proceso[];
  selectedProceso: Proceso;

  constructor(
    private titleService: Title,
    private procesoService: ProcesoService) {
      this.procesoService.findAll().subscribe(procesos => {
        this.procesos = procesos;
        this.selectedProceso = procesos[procesos.length - 1];
        console.log(this.selectedProceso);
      });
    }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento');
  }

}
