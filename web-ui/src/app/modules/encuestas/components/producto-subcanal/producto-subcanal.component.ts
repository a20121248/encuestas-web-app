import { Component, OnInit, Input, ViewChild, ɵConsole } from '@angular/core';
import { Usuario } from 'src/app/shared/models/usuario';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';

@Component({
  selector: 'app-form-producto-subcanal',
  templateUrl: './producto-subcanal.component.html',
  styleUrls: ['./producto-subcanal.component.scss']
})
export class ProductoSubcanalComponent implements OnInit {
  @Input() lstProductoSubcanales: ObjetoObjetos[];

  lstCabeceraTableObtenida: string[];
  lstCabeceraTableDynamico: string[];
  sumaTotal: number;

  constructor(
  ) { }

  ngOnInit() {
    console.log(this.lstProductoSubcanales);
    this.obtenerNombresColumna();
  }

  obtenerNombresColumna() {
    if (this.lstProductoSubcanales != null) {
      this.lstCabeceraTableObtenida = ['productos'];
      this.lstCabeceraTableDynamico = this.lstProductoSubcanales[0].lstObjetos.map(
        (subcanal) => {
          return subcanal.nombre;
        }
      );
      this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
      this.lstCabeceraTableObtenida.push('total');
    }
  }

  obtenerSuma(element: ObjetoObjetos): number {
    if (element != null) {
      return Math.trunc(100000 * element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0)) / 100000;
    }
    return 0;
  }

  obtenerSumaTotal(): number {
    this.sumaTotal = 0;
    if (this.lstProductoSubcanales != null) {
      this.lstProductoSubcanales.forEach(element => {
        this.sumaTotal += element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      });
      return Math.trunc(100000 * this.sumaTotal) / 100000;
    }
    return 0;
  }
}
