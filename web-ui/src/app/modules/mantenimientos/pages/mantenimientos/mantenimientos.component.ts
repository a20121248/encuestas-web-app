import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

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

  constructor(
    private titleService: Title) { }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Mantenimiento');
  }

}
