import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-mantenimientos',
  templateUrl: './mantenimientos.component.html',
  styleUrls: ['./mantenimientos.component.css']
})
export class MantenimientosComponent implements OnInit {
  titulo = 'Módulo de Mantenimiento';
  constructor(
    private titleService: Title) { }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Mantenimiento');
  }

}
