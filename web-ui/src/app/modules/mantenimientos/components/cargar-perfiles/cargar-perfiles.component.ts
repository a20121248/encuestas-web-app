import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-cargar-perfiles',
  templateUrl: './cargar-perfiles.component.html',
  styleUrls: ['./cargar-perfiles.component.css']
})
export class CargarPerfilesComponent implements OnInit {
  titulo = 'CARGAR PERFILES';
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  constructor() { }

  ngOnInit() {
  }

}
