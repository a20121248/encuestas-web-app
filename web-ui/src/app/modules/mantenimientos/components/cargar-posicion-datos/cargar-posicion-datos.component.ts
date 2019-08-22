import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-cargar-posicion-datos',
  templateUrl: './cargar-posicion-datos.component.html',
  styleUrls: ['./cargar-posicion-datos.component.css']
})
export class CargarPosicionDatosComponent implements OnInit {
  titulo = 'CARGAR DATOS DE LAS POSICIONES';
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  constructor() { }

  ngOnInit() {
  }

}
