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
  }

  ngOnDestroy(): void {
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '450px';
    dialogConfig.data = {
      titulo: `Crear encuesta`,
      estados: this.estados
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(proceso => proceso))
        .subscribe(proceso => {
          this.procesoService.crear(proceso).subscribe((response) => {
            this.procesos.push(response);
            this.table.renderRows();
          }, err => console.log(err)
          );
        });
  }

  editar(): void {
    if (this.selectedProceso == null) {
      swal.fire('Editar encuesta', 'Por favor, seleccione una encuesta.', 'error');
      return;
    }

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '450px';
    dialogConfig.data = {
      titulo: `Editar encuesta ${this.selectedProceso.codigo}`,
      estados: this.estados,
      proceso: this.selectedProceso
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(proceso => proceso))
        .subscribe(proceso => {
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
            this.selectedProceso = null;
            swal.fire(`Eliminar encuesta ${this.selectedProceso.codigo}`, 'La encuesta ha sido eliminada.', 'success');
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
