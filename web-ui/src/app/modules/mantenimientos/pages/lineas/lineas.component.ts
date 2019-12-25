import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Objeto } from 'src/app/shared/models/objeto';
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
  selector: 'app-lineas',
  templateUrl: './lineas.component.html',
  styleUrls: ['./lineas.component.scss']
})
export class LineasComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  lineas: Objeto[];
  selectedIndex: number;
  selectedLinea: Objeto;
  dcLineas = ['codigo', 'nombre', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeLineas: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;

  constructor(private titleService: Title,
              private lineaService: LineaService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE LÍNEAS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Líneas');
    this.lineaService.findAll().subscribe(lineas => {
      this.lineas = lineas;
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
  }

  ngOnDestroy(): void {
    if (this.subscribeLineas) this.subscribeLineas.unsubscribe();
    if (this.subscribeEliminar) this.subscribeEliminar.unsubscribe();
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear línea`,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(linea => linea)).subscribe(linea => {
      this.lineaService.create(linea).subscribe((response) => {
        this.lineas.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedLinea == null) {
      swal.fire('Editar línea', 'Por favor, seleccione una línea.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedLinea.codigo;
    this.campoNombre.value = this.selectedLinea.nombre;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar línea '${this.selectedLinea.codigo}'`,
      item: this.selectedLinea,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(linea => linea)).subscribe(linea => {
      this.lineaService.edit(linea).subscribe((response) => {
        this.lineas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedLinea == null) {
      swal.fire('Deshabilitar línea', 'Por favor, seleccione una línea.', 'error');
    } else if (this.selectedLinea.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.lineaService.softDelete(this.selectedLinea).subscribe(response => {
        this.lineas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.lineaService.softUndelete(this.selectedLinea).subscribe(response => {
        this.lineas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedLinea == null) {
      swal.fire('Eliminar línea', 'Por favor, seleccione una línea.', 'error');
    } else {
      swal.fire({
        title: `Eliminar línea '${this.selectedLinea.codigo}'`,
        text: `¿Está seguro de eliminar la línea '${this.selectedLinea.nombre}'?`,
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
          this.subscribeEliminar = this.lineaService.delete(this.selectedLinea).subscribe(res => {
            this.lineas.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar línea '${this.selectedLinea.codigo}'`, 'La línea ha sido eliminada.', 'success');
            this.selectedLinea = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar líneas`,
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
        this.subscribeEliminar = this.lineaService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar líneas`, 'Las líneas han sido eliminadas.', 'success');
        });
      }
    });
  }

  setSelected(linea: Objeto, i: number) {
    this.selectedIndex = i;
    this.selectedLinea = linea;
  }
}
