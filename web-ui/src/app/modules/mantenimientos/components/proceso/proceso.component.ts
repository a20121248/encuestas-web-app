import { Component, Inject, OnInit } from '@angular/core';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig} from '@angular/material/dialog';
import swal from 'sweetalert2';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Usuario } from 'src/app/shared/models/usuario';
import { filter } from 'rxjs/operators';
import { ModalCrearComponent } from '../modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../modal-eliminar/modal-eliminar.component';

@Component({
  selector: 'app-proceso',
  templateUrl: './proceso.component.html',
  styleUrls: ['./proceso.component.scss']
})
export class ProcesoComponent implements OnInit {
  titulo: string;
  procesos: Proceso[];
  selectedProceso: Proceso;
  dcProcesos = ['codigo', 'nombre', 'creador', 'fechaCierre', 'fechaCreacion', 'fechaActualizacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;

  constructor(
    private procesoService: ProcesoService,
    public dialog: MatDialog
  ) {
    this.titulo = 'CONFIGURACIÓN DE PROCESOS';
  }

  ngOnInit() {
    this.procesoService.findAll().subscribe(procesos => {
      this.procesos = procesos;
    });
  }

  crear(): void {
    const param = {
      hasBackdrop: true,
      width: '500px',
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, param);
    this.crearDialogRef
        .afterClosed()
        .pipe(filter(name => name))
        .subscribe(name => {
          console.log(name);
          // this.files.push({name, content: '' });
        });
  }

  editar() {
    if (this.selectedProceso == null) {
      swal.fire('Editar proceso', 'Por favor, seleccione un proceso.', 'error');
      return;
    }

    const data = {
      titulo: 'titulo'
    };
    const param = {
      hasBackdrop: true,
      width: '500px',
      data: {
        titulo: 'titulo'
      }
    };
    this.editarDialogRef = this.dialog.open(ModalCrearComponent, param);
    this.editarDialogRef
        .afterClosed()
        .pipe(filter(name => name))
        .subscribe(name => {
          console.log(name);
          // this.files.push({name, content: '' });
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

  setSelected(proceso: Proceso) {
    this.selectedProceso = proceso;
  }
}

@Component({
  selector: 'app-proceso-dialog',
  templateUrl: 'dialog.html',
})
export class ProcesoDialogComponent {
  formGroup: FormGroup;
  description: string;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ProcesoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.description = data.description;
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      codigo: [, []],
      nombre: [, []],
      creador: [, []],
      fechaCierre: [, []]
    });
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.dialogRef.close(this.formGroup.value);
  }
}
