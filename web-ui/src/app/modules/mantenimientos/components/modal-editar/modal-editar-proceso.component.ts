import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Proceso } from 'src/app/shared/models/Proceso';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';

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
    { provide: MAT_RADIO_DEFAULT_OPTIONS, useValue: { color: 'primary' }}
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
      codigo: [this.proceso.codigo, Validators.required],
      nombre: [this.proceso.nombre, Validators.required],
      estado: [this.proceso.activo ? '1' : '2', Validators.required],
      fechaInicio: [this.proceso.fechaInicio, Validators.required],
      fechaCierre: [this.proceso.fechaCierre, Validators.required]
    });
  }

  get codigo() { return this.formGroup.get('codigo'); }
  get nombre() { return this.formGroup.get('nombre'); }
  get estado() { return this.formGroup.get('estado'); }
  get fechaInicio() { return this.formGroup.get('fechaInicio'); }
  get fechaCierre() { return this.formGroup.get('fechaCierre'); }

  get errorFechas() {
    if (this.fechaInicio.value == null || this.fechaCierre.value == null) {
      return false;
    }
    if (this.fechaInicio.value < this.fechaCierre.value) {
      return false;
    }
    return true;
  }

  submit() {
    if (this.formGroup.valid && !this.errorFechas) {
      this.formGroup.value.id = this.proceso.id;
      this.formGroup.value.activo = this.formGroup.value.estado == '1' ? true : false;
      this.formGroup.value.fechaCreacion = this.proceso.fechaCreacion;
      this.formGroup.value.fechaActualizacion = this.proceso.fechaActualizacion;
      this.dialogRef.close(this.formGroup.value);
    } else {
      this.codigo.markAsTouched({ onlySelf: true });
      this.nombre.markAsTouched({ onlySelf: true });
      this.estado.markAsTouched({ onlySelf: true });
      this.fechaInicio.markAsTouched({ onlySelf: true });
      this.fechaCierre.markAsTouched({ onlySelf: true });
    }
  }
}
