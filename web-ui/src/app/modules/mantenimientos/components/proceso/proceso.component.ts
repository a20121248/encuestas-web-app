import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { MatDialog, MatDialogRef, MatDialogConfig} from '@angular/material/dialog';
import swal from 'sweetalert2';
import { filter } from 'rxjs/operators';
import { ModalCrearComponent } from '../modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../modal-eliminar/modal-eliminar.component';
import { MatTable } from '@angular/material/table';


@Component({
  selector: 'app-proceso',
  templateUrl: './proceso.component.html',
  styleUrls: ['./proceso.component.scss']
})
export class ProcesoComponent implements OnInit {
  titulo: string;
  procesos: Proceso[];
  selectedIndex: number;
  selectedProceso: Proceso;
  dcProcesos = ['codigo', 'nombre', 'creador', 'fechaCierre', 'fechaCreacion', 'fechaActualizacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;


  @ViewChild(MatTable, { static: false }) table: MatTable<any>;

  constructor(
    private procesoService: ProcesoService,
    public dialog: MatDialog
  ) {
    this.titulo = 'CONFIGURACIÓN DE ENCUESTAS';
  }

  ngOnInit() {
    this.procesoService.findAll().subscribe(procesos => {
      this.procesos = procesos;
    });
  }



  crear(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '500px';
    dialogConfig.data = {
      titulo: `Crear encuesta`
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed()
        .pipe(filter(proceso => proceso))
        .subscribe(proceso => {
          console.log('creando el proceso: ');
          console.log(proceso);
          this.procesoService.crear(proceso).subscribe((response) => {
            this.procesos.push(response);
            console.log(this.procesos);
            this.table.renderRows();
          }, err => console.log(err)
          );
        });
  }

  editar(): void {
    if (this.selectedProceso == null) {
      swal.fire('Editar encuesta', 'Por favor, seleccione un proceso.', 'error');
      return;
    }

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '500px';
    dialogConfig.data = {
      titulo: `Editar encuesta ${this.selectedProceso.codigo}`,
      proceso: this.selectedProceso
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed()
        .pipe(filter(proceso => proceso))
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
      swal.fire('Eliminar proceso', 'Por favor, seleccione un proceso.', 'error');
      return;
    } else {
      swal.fire('Eliminar proceso', `¿Está seguro de eliminar el proceso ${this.selectedProceso.nombre}?` , 'question');
      return;
    }
  }

  setSelected(proceso: Proceso, i: number) {
    this.selectedIndex = i;
    this.selectedProceso = proceso;
  }
}
