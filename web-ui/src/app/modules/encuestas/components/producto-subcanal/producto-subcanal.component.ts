import { Component, OnInit, Input, ViewChild } from '@angular/core';


@Component({
  selector: 'app-form-producto-subcanal',
  templateUrl: './producto-subcanal.component.html',
  styleUrls: ['./producto-subcanal.component.css']
})
export class ProductoSubcanalComponent implements OnInit {
  
  @Input() matriz: any[];
  
  lstCabeceraTableObtenida: string[];
  lstCabeceraTableDynamico: string[];
  sumaTotal:number;
  constructor(
  ) {}

  ngOnInit() {
    this.obtenerNombresColumna();
  }

  obtenerNombresColumna(){
    this.lstCabeceraTableDynamico=this.matriz[0].lstSubcanales.map(sC=> sC.nombre);
    this.lstCabeceraTableObtenida=['nombreProducto'];
    this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
    this.lstCabeceraTableObtenida.push('Total');
  }
  obtenerSuma(element: any):number{
    if(element!=null){
      return Math.trunc(100000*element.lstSubcanales.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0))/100000;
    }
    else return 0;
      
  }
  obtenerSumaTotal():number{
    this.sumaTotal=0;
    if(this.matriz!= null){
      this.matriz.forEach(element => {
        this.sumaTotal+=element.lstSubcanales.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      });
      return Math.trunc(100000*this.sumaTotal)/100000;
    }
    else return 0;
  }
}
