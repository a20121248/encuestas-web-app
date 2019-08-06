import { Component, OnInit, Input } from '@angular/core';
import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { Usuario } from 'src/app/shared/models/usuario';


@Component({
  selector: 'app-form-producto-canal',
  templateUrl: './producto-canal.component.html',
  styleUrls: ['./producto-canal.component.css']
})
export class ProductoCanalComponent implements OnInit {

  @Input() matriz: ProductoCanal[];
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
    if (this.matriz != null) {
      this.lstCabeceraTableDynamico = this.matriz[0].lstCanales.map(sC => sC.nombre);
      this.lstCabeceraTableObtenida = ['nombreProducto'];
      this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
      this.lstCabeceraTableObtenida.push('Total');
    }
  }

  obtenerSuma(element: any): number {
    if (element != null) {
      return Math.trunc(100000 * element.lstCanales.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0)) / 100000;
    }
    return 0;
  }

  obtenerSumaTotal(): number {
    this.sumaTotal = 0;
    if (this.matriz != null) {
      this.matriz.forEach(element => {
        this.sumaTotal += element.lstCanales.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      });
      return Math.trunc(100000 * this.sumaTotal) / 100000;
    }
    return 0;
  }
}
