import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';

import { Linea } from 'src/app/shared/models/linea';
import { LineaService } from 'src/app/shared/services/linea.service';
import { HttpClient } from '@angular/common/http';
import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { Usuario } from 'src/app/shared/models/usuario';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';

@Component({
  selector: 'app-form-linea-canal',
  templateUrl: './linea-canal.component.html',
  styleUrls: ['./linea-canal.component.css'],
  // animations: [
  //   trigger('rowClicked', [
  //     state('selected', style({ background: 'lightblue' })),
  //     state('unselected', style({ background: 'yellow' })),
  //     transition('selected <=> unselected', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
  //   ]),
  // ],
})
export class LineaCanalComponent implements OnInit {
  @Input() lstLineaCanales: ObjetoObjetos[];
  @Output() sendLinea = new EventEmitter();
  selectedElement: ObjetoObjetos | null;

  dcLinea = ['codigo', 'nombre', 'porcentaje'];

  constructor(
    private lineaService: LineaService,
    private http: HttpClient
  ) { }

  ngOnInit() {
  }

  getTotalPorcentaje() {
    if (this.lstLineaCanales != null) {
      return this.lstLineaCanales.map(t => t.objeto.porcentaje).reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }

  showCanalesBylineaBoton(objeto: ObjetoObjetos) {
    this.sendLinea.emit(objeto);
    this.selectedElement = objeto;
  }

  // setClickedRow = function (index: number) {
  //   this.selectedRow = index;
  // }
}
