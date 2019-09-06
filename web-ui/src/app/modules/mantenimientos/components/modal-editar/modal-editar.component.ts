import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Proceso } from 'src/app/shared/models/Proceso';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { MomentDateAdapter } from '@angular/material-moment-adapter';

export const MY_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-modal-editar',
  templateUrl: './modal-editar.component.html',
  styleUrls: ['./modal-editar.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ]
})
export class ModalEditarComponent implements OnInit {
  formGroup: FormGroup;
  titulo: string;
  proceso: Proceso;
  estados: string[];
  selectedEstado: string;
  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalEditarComponent>,
              @Inject(MAT_DIALOG_DATA) private data) { }

  ngOnInit() {
    this.estados = this.data.estados;
    this.titulo = this.data.titulo;
    this.proceso = this.data.proceso;
    this.formGroup = this.formBuilder.group({
      id: this.proceso.id,
      codigo: this.proceso.codigo,
      nombre: this.proceso.nombre,
      usuario: this.proceso.usuario,
      fechaInicio: this.proceso.fechaInicio,
      fechaCierre: this.proceso.fechaCierre,
      fechaCreacion: this.proceso.fechaCreacion,
      fechaActualizacion: this.proceso.fechaActualizacion
    });
  }

  submit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
