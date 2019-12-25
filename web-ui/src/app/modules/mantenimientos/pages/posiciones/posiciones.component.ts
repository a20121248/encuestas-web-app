import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Posicion } from 'src/app/shared/models/posicion';
import { PosicionService } from 'src/app/shared/services/posicion.service';
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
  selector: 'app-posiciones',
  templateUrl: './posiciones.component.html',
  styleUrls: ['./posiciones.component.scss']
})
export class PosicionesComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  posiciones: Posicion[];
  selectedIndex: number;
  selectedPosicion: Posicion;
  dcPosiciones = ['codigo', 'nombre', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribePosiciones: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;

  constructor(private titleService: Title,
              private posicionService: PosicionService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'POSICIONES';

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

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Posiciones');
    this.subscribePosiciones = this.posicionService.findAll().subscribe(posiciones => {
      this.posiciones = posiciones;
    });
  }

  ngOnDestroy(): void {
    this.subscribePosiciones.unsubscribe();
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear posición`,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(posicion => posicion)).subscribe(posicion => {
      this.posicionService.create(posicion).subscribe((response) => {
        this.posiciones.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedPosicion == null) {
      swal.fire('Editar posición', 'Por favor, seleccione una posición.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedPosicion.codigo;
    this.campoNombre.value = this.selectedPosicion.nombre;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar posición '${this.selectedPosicion.codigo}'`,
      item: this.selectedPosicion,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(posicion => posicion)).subscribe(posicion => {
      this.posicionService.edit(posicion).subscribe((response) => {
        this.posiciones[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedPosicion == null) {
      swal.fire('Deshabilitar posición', 'Por favor, seleccione una posición.', 'error');
    } else if (this.selectedPosicion.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.posicionService.softDelete(this.selectedPosicion).subscribe(response => {
        this.posiciones[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.posicionService.softUndelete(this.selectedPosicion).subscribe(response => {
        this.posiciones[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedPosicion == null) {
      swal.fire('Eliminar posición', 'Por favor, seleccione una posición.', 'error');
    } else {
      swal.fire({
        title: `Eliminar posición '${this.selectedPosicion.codigo}'`,
        text: `¿Está seguro de eliminar la posición '${this.selectedPosicion.nombre}'?`,
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
          this.subscribeEliminar = this.posicionService.delete(this.selectedPosicion).subscribe(res => {
            this.posiciones.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar posición '${this.selectedPosicion.codigo}'`, 'La posición ha sido eliminada.', 'success');
            this.selectedPosicion = null;
          });
        }
      });
    }
  }
  
  limpiar(): void {
    swal.fire({
      title: `Eliminar posiciones`,
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
        this.subscribeEliminar = this.posicionService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar posiciones`, 'Las posiciones han sido eliminadas.', 'success');
        });
      }
    });
  }

  setSelected(posicion: Posicion, i: number) {
    this.selectedIndex = i;
    this.selectedPosicion = posicion;
  }
}
