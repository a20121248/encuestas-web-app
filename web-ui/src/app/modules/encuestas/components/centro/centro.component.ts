import { Component, OnInit, Input } from '@angular/core';

import { Centro } from 'src/app/shared/models/centro';
import { Usuario } from 'src/app/shared/models/usuario';

@Component({
  selector: 'app-form-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css']
})

export class CentroComponent implements OnInit {
  @Input() lstCentros: Centro[];
  @Input() usuarioSeleccionado: Usuario;
  dcCentro = ['codigo', 'nombre', 'nivel', 'porcentaje'];

  constructor() { }

  ngOnInit() {
    this.lstCentros = [];
}

  getTotalPorcentaje(){
    return this.lstCentros.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }
}
