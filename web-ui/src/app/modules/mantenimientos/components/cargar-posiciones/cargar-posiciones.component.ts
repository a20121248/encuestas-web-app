import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-cargar-posiciones',
  templateUrl: './cargar-posiciones.component.html',
  styleUrls: ['./cargar-posiciones.component.css']
})
export class CargarPosicionesComponent implements OnInit {
  titulo = 'CARGAR POSICIONES';
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  constructor() { }

  ngOnInit() {
  }

}
