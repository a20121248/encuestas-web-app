import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-areas',
  templateUrl: './areas.component.html',
  styleUrls: ['./areas.component.scss']
})
export class AreasComponent implements OnInit {
  titulo: string;

  constructor() {
    this.titulo = 'LISTADO DE ÁREAS';
  }

  ngOnInit() {
  }

}
