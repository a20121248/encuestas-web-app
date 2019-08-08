import { Component, OnInit, Input } from '@angular/core';
import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { Usuario } from 'src/app/shared/models/usuario';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';


@Component({
  selector: 'app-form-producto-canal',
  templateUrl: './producto-canal.component.html',
  styleUrls: ['./producto-canal.component.css']
})
export class ProductoCanalComponent implements OnInit {
  @Input() lstProductoCanales: ObjetoObjetos[];
  @Input() usuarioSeleccionado: Usuario;

  lstCabeceraTableObtenida: string[];
  lstCabeceraTableDynamico: string[];
  sumaTotal: number;

  constructor(
  ) { }

  ngOnInit() {
    this.obtenerNombresColumna();
  }

  obtenerNombresColumna() {
    if (this.lstProductoCanales != null) {
      this.lstCabeceraTableObtenida = ['productos'];
      this.lstCabeceraTableDynamico = this.lstProductoCanales[0].lstObjetos.map(
        (subcanal) => {
          return subcanal.nombre;
        }
      );
      this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
      this.lstCabeceraTableObtenida.push('Total');
    }
    console.log(this.lstProductoCanales);
    console.log(this.lstCabeceraTableObtenida);
  }

  obtenerSuma(element: any): number {
    if (element != null) {
      return Math.trunc(100000 * element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0)) / 100000;
    }
    return 0;
  }

  obtenerSumaTotal(): number {
    this.sumaTotal = 0;
    if (this.lstProductoCanales != null) {
      this.lstProductoCanales.forEach(element => {
        this.sumaTotal += element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      });
      return Math.trunc(100000 * this.sumaTotal) / 100000;
    }
    return 0;
  }
}
