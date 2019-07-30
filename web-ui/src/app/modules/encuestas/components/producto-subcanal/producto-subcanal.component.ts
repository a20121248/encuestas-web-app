import { Component, OnInit, Input } from '@angular/core';
import { Subcanal } from 'src/app/shared/models/subcanal';
import { Producto } from 'src/app/shared/models/producto';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ProductoService } from 'src/app/shared/services/producto.service';
import { SubcanalService } from 'src/app/shared/services/subcanal.service';
import { ProductoSubcanalService } from 'src/app/shared/services/producto-subcanal.service';

@Component({
  selector: 'app-producto-subcanal',
  templateUrl: './producto-subcanal.component.html',
  styleUrls: ['./producto-subcanal.component.css']
})
export class ProductoSubcanalComponent implements OnInit {

  @Input() lstProductoSubcanal: ProductoSubcanal[] ;
  @Input() lstProducto: Producto[];
  @Input() lstSubcanal: Subcanal[];
  lstCabeceraTable: string[]= ["nombreLinea"];
  constructor(
    private lineaSubcanalService: ProductoSubcanalService,
    private productoService: ProductoService,
    private subcanalService: SubcanalService
  ) { }

  ngOnInit() {
    this.lstProductoSubcanal = [];
    this.lstProducto= [];
    this.lstSubcanal = [];
  }

  setUpCabeceraTabla():void{
    this.lstSubcanal.forEach(element => {
      this.lstCabeceraTable.push(element.nombre);
    });
  }

  getLstProductoSubcanal(): ProductoSubcanal[] {
    return this.lstProductoSubcanal;
  }

  getLstProducto(): Producto[] {
    return this.lstProducto;
  }

  getLstSubcanal(): Subcanal[] {
    return this.lstSubcanal;
  }
}
