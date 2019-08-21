import { Component, Inject, OnInit } from '@angular/core';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig} from '@angular/material/dialog';
import swal from 'sweetalert2';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-proceso',
  templateUrl: './proceso.component.html',
  styleUrls: ['./proceso.component.css']
})
export class ProcesoComponent implements OnInit {
  titulo = 'CONFIGURACIÓN DE PROCESOS';
  procesos: Proceso[];
  selectedProceso: Proceso;
  dcProcesos = ['codigo', 'nombre', 'creador', 'fechaCierre', 'fechaCreacion', 'fechaActualizacion'];

  description: string;


  constructor(
    private procesoService: ProcesoService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.procesoService.findAll().subscribe(procesos => {
      this.procesos = procesos;
    });
    console.log(this.procesos);
  }

  crear(): void {

  }

  editar() {
    if (this.selectedProceso == null) {
      swal.fire('Editar proceso', 'Por favor, seleccione un proceso.', 'error');
      return;
    }

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: 1,
      title: 'Angular For Beginners'
    };

    this.dialog.open(ProcesoDialogComponent, dialogConfig);

    const dialogRef = this.dialog.open(ProcesoDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => console.log('Dialog output:', data)
    );
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
  form: FormGroup;
  description: string;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ProcesoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.description = data.description;
  }

  ngOnInit() {
    this.form = this.fb.group({
      description: [this.description, []]
    });
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    this.dialogRef.close(this.form.value);
  }

}
