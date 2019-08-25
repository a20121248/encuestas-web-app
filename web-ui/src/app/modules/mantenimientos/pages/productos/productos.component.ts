import { Component, OnInit } from '@angular/core';
import { Objeto } from 'src/app/shared/models/objeto';
import { Title } from '@angular/platform-browser';
import { ProductoService } from 'src/app/shared/services/producto.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  tituloPagina: string;
  titulo: string;
  productos: Objeto[];
  selectedProducto: Objeto;
  dcProductos = ['codigo', 'nombre', 'lineaCodigo', 'lineaNombre', 'fechaCreacion', 'fechaActualizacion'];

  constructor(private titleService: Title,
              private productoService: ProductoService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE PRODUCTOS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Productos');
    this.productoService.findAll().subscribe(productos => {
      this.productos = productos;
    });
  }

  setSelected(producto: Objeto) {
    this.selectedProducto = producto;
  }
}
