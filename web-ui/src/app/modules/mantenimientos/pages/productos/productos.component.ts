import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Objeto } from 'src/app/shared/models/objeto';
import { ProductoService } from 'src/app/shared/services/producto.service';
import { LineaService } from 'src/app/shared/services/linea.service';
import { MatTable } from '@angular/material/table';
import { MatDialog, MatDialogRef, MatDialogConfig} from '@angular/material/dialog';
import { ModalCrearComponent } from '../../components/modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../../components/modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../../components/modal-eliminar/modal-eliminar.component';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Campo } from 'src/app/shared/models/campo';
import swal from 'sweetalert2';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.scss']
})
export class ProductosComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  productos: Objeto[];
  lineas: Objeto[];
  selectedIndex: number;
  selectedProducto: Objeto;
  dcProductos = ['codigo', 'nombre', 'lineaCodigo', 'lineaNombre', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeProductos: Subscription;
  subscribeLineas: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;
  campoLinea: Campo;

  constructor(private titleService: Title,
              private productoService: ProductoService,
              private lineaService: LineaService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE PRODUCTOS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Productos');
    this.productoService.findAll().subscribe(productos => {
      this.productos = productos;
    });

    this.campoCodigo = new Campo();
    this.campoCodigo.name = 'codigo';
    this.campoCodigo.label = 'Código';
    this.campoCodigo.type = 'text';
    this.campoCodigo.width = 15;
    this.campoCodigo.maxLength = 8;
    this.campoCodigo.messages = ['Ingrese un código'];

    this.campoNombre = new Campo();
    this.campoNombre.name = 'nombre';
    this.campoNombre.label = 'Nombre';
    this.campoNombre.type = 'text';
    this.campoNombre.width = 80;
    this.campoNombre.maxLength = 200;
    this.campoNombre.messages = ['Ingrese un nombre'];

    this.campoLinea = new Campo();
    this.campoLinea.name = 'objetoPadre';
    this.campoLinea.label = 'Línea de negocio';
    this.campoLinea.type = 'select';
    this.campoLinea.width = 100;
    this.subscribeLineas = this.lineaService.findAll().subscribe(lineas => {
      this.campoLinea.items = lineas;
    }, err => {
      console.log(err);
    });
    this.campoLinea.value = null;
    this.campoLinea.messages = ['Seleccione la línea a la que pertenece'];
  }

  ngOnDestroy(): void {
    if (this.subscribeProductos) this.subscribeProductos.unsubscribe();
    if (this.subscribeLineas) this.subscribeLineas.unsubscribe();
    if (this.subscribeEliminar) this.subscribeEliminar.unsubscribe();
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";
    this.campoLinea.value = null;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear producto`,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoLinea]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(producto => producto)).subscribe(producto => {
      this.productoService.create(producto).subscribe((response) => {
        this.productos.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedProducto == null) {
      swal.fire('Editar producto', 'Por favor, seleccione un producto.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedProducto.codigo;
    this.campoNombre.value = this.selectedProducto.nombre;
    this.campoLinea.value = this.campoLinea.items.find(e => e.id == this.selectedProducto.objetoPadre.id);

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar producto '${this.selectedProducto.codigo}'`,
      item: this.selectedProducto,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoLinea]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(producto => producto)).subscribe(producto => {
      this.productoService.edit(producto).subscribe((response) => {
        this.productos[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedProducto == null) {
      swal.fire('Deshabilitar producto', 'Por favor, seleccione un producto.', 'error');
    } else if (this.selectedProducto.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.productoService.softDelete(this.selectedProducto).subscribe(response => {
        this.productos[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.productoService.softUndelete(this.selectedProducto).subscribe(response => {
        this.productos[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedProducto == null) {
      swal.fire('Eliminar producto', 'Por favor, seleccione un producto.', 'error');
    } else {
      swal.fire({
        title: `Eliminar producto '${this.selectedProducto.codigo}'`,
        text: `¿Está seguro de eliminar el producto '${this.selectedProducto.nombre}'? Esta acción es irreversible.`,
        type: 'warning',
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar'
      }).then((result) => {
        if (result.value) {
          if (this.subscribeEliminar != null) {
            this.subscribeEliminar.unsubscribe();
          }
          this.subscribeEliminar = this.productoService.delete(this.selectedProducto).subscribe(res => {
            this.productos.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar producto '${this.selectedProducto.codigo}'`, 'El producto ha sido eliminado.', 'success');
            this.selectedProducto = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar productos`,
      text: 'Esta acción es irreversible.',
      type: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar'
    }).then((result) => {
      if (result.value) {
        if (this.subscribeEliminar != null) {
          this.subscribeEliminar.unsubscribe();
        }
        this.subscribeEliminar = this.productoService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar productos`, 'Los productos han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(producto: Objeto, i: number) {
    this.selectedIndex = i;
    this.selectedProducto = producto;
  }
}
