import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { MatDialog, MatDialogRef, MatDialogConfig} from '@angular/material/dialog';
import swal from 'sweetalert2';
import { filter } from 'rxjs/operators';
import { ModalCrearComponent } from '../modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../modal-eliminar/modal-eliminar.component';
import { MatTable } from '@angular/material/table';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Subscription } from 'rxjs';
import { Campo } from 'src/app/shared/models/campo';
import { Tipo } from 'src/app/shared/models/tipo';

@Component({
  selector: 'app-proceso',
  templateUrl: './proceso.component.html',
  styleUrls: ['./proceso.component.scss']
})
export class ProcesoComponent implements OnInit, OnDestroy {
  titulo: string;
  procesos: Proceso[];
  selectedIndex: number;
  selectedProceso: Proceso;
  estados: string[];
  dcProcesos = ['codigo', 'nombre', 'creador', 'activo', 'fechaInicio', 'fechaCierre', 'fechaCreacion', 'fechaActualizacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;
  campoEstado: Campo;
  campoFechaInicio: Campo;
  campoFechaCierre: Campo;
  
  constructor(
    public authService: AuthService,
    private procesoService: ProcesoService,
    public dialog: MatDialog
  ) {
    this.titulo = 'CONFIGURACIÓN DE ENCUESTAS';
    this.estados = ['Abierta', 'Cerrada'];
  }

  ngOnInit() {
    this.procesoService.findAll().subscribe(procesos => {
      this.procesos = procesos;
    });

    //'codigo', 'nombre', 'creador', 'activo', 'fechaInicio', 'fechaCierre', 'fechaCreacion', 'fechaActualizacion'
    this.campoCodigo = new Campo();
    this.campoCodigo.name = 'codigo';
    this.campoCodigo.label = 'Código';
    this.campoCodigo.type = 'text';
    this.campoCodigo.width = 20;
    this.campoCodigo.maxLength = 7;
    this.campoCodigo.messages = ['Ingrese un código'];

    this.campoNombre = new Campo();
    this.campoNombre.name = 'nombre';
    this.campoNombre.label = 'Nombre';
    this.campoNombre.type = 'text';
    this.campoNombre.width = 75;
    this.campoNombre.maxLength = 200;
    this.campoNombre.messages = ['Ingrese un nombre'];

    this.campoEstado = new Campo();
    this.campoEstado.name = 'estado';
    this.campoEstado.label = 'Seleccionar una opción';
    this.campoEstado.type = 'radio';
    this.campoEstado.width = 80;
    this.campoEstado.items = [new Tipo(1, 'ABIERTA', null), new Tipo(2, 'CERRADA', null)];

    this.campoFechaInicio = new Campo();
    this.campoFechaInicio.name = 'fechaInicio';
    this.campoFechaInicio.label = 'Fecha de inicio';
    this.campoFechaInicio.type = 'date';
    this.campoFechaInicio.width = 45;
    this.campoFechaInicio.messages = ['Fecha de inicio'];

    this.campoFechaCierre = new Campo();
    this.campoFechaCierre.name = 'fechaCierre';
    this.campoFechaCierre.label = 'Fecha de cierre';
    this.campoFechaCierre.type = 'date';
    this.campoFechaCierre.width = 45;
    this.campoFechaCierre.messages = ['Fecha de cierre'];
  }

  ngOnDestroy(): void {
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    this.campoCodigo.value = '';
    this.campoNombre.value = '';
    this.campoEstado.value = this.campoEstado.items[0];

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '450px';
    dialogConfig.data = {
      titulo: `Crear encuesta`,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoEstado],
        [this.campoFechaInicio, this.campoFechaCierre]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(proceso => proceso)).subscribe(proceso => {
      proceso.activo = proceso.estado.nombre == 'ABIERTA' ? true : false;
      this.procesoService.crear(proceso).subscribe((response) => {
        this.procesos.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedProceso == null) {
      swal.fire('Editar encuesta', 'Por favor, seleccione una encuesta.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedProceso.codigo;
    this.campoNombre.value = this.selectedProceso.nombre;
    this.campoEstado.value = this.selectedProceso.activo ? this.campoEstado.items[0] : this.campoEstado.items[1];
    this.campoFechaInicio.value = this.selectedProceso.fechaInicio;
    this.campoFechaCierre.value = this.selectedProceso.fechaCierre;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '450px';
    dialogConfig.data = {
      titulo: `Editar encuesta ${this.selectedProceso.codigo}`,
      item: this.selectedProceso,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoEstado],
        [this.campoFechaInicio, this.campoFechaCierre]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(proceso => proceso)).subscribe(proceso => {
      proceso.activo = proceso.estado.nombre == 'ABIERTA' ? true : false;
      this.procesoService.editar(proceso).subscribe((response) => {
        this.procesos[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => console.log(err)
      );
    });
  }

  eliminar() {
    if (this.selectedProceso == null) {
      swal.fire('Eliminar encuesta', 'Por favor, seleccione una encuesta.', 'error');
    } else if (this.selectedProceso.activo) {
      swal.fire('Eliminar encuesta', 'No puede eliminar una encuesta abierta. En caso desee hacerlo, primero debe cerrarla.', 'error');
    } else {
      swal.fire({
        title: `Eliminar encuesta '${this.selectedProceso.codigo}'`,
        text: `¿Está seguro de eliminar la encuesta '${this.selectedProceso.nombre}'?`,
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
          this.subscribeEliminar = this.procesoService.delete(this.selectedProceso).subscribe(res => {
            this.procesos.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar encuesta ${this.selectedProceso.codigo}`, 'La encuesta ha sido eliminada.', 'success');
            this.selectedProceso = null;
          });
        }
      });
    }
  }

  setSelected(proceso: Proceso, i: number) {
    this.selectedIndex = i;
    this.selectedProceso = proceso;
  }
}
