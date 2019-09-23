import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DateAdapter, MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { Campo } from 'src/app/shared/models/campo';

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
  selector: 'app-modal-crear',
  templateUrl: './modal-crear.component.html',
  styleUrls: ['./modal-crear.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
    { provide: MAT_RADIO_DEFAULT_OPTIONS, useValue: { color: 'primary' }}
  ]
})
export class ModalCrearComponent implements OnInit {
  formGroup: FormGroup;
  titulo: string;
  campos: Campo[];
  estados: string[];
  selectedEstado: string;

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalCrearComponent>,
              @Inject(MAT_DIALOG_DATA) private data) { }

  ngOnInit() {
    this.titulo = this.data.titulo;

    this.formGroup = this.formBuilder.group({});
    this.formGroup.addControl('estado', new FormControl('1', Validators.required));
    this.formGroup.addControl('fechaInicio', new FormControl(null, Validators.required));
    this.formGroup.addControl('fechaCierre', new FormControl(null, Validators.required));

    this.data.camposArr.forEach(campos => {
      campos.forEach(campo => {
        this.formGroup.addControl(campo.name, new FormControl(campo.value, Validators.required));
      });
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
      this.formGroup.value.activo = this.formGroup.value.estado == '1' ? true : false;
      const fecha = new Date();
      this.formGroup.value.fechaCreacion = fecha;
      this.formGroup.value.fechaActualizacion = fecha;
      this.dialogRef.close(this.formGroup.value);
    } else {
      this.formGroup.markAllAsTouched();
      /*this.codigo.markAsTouched({ onlySelf: true });
      this.nombre.markAsTouched({ onlySelf: true });
      this.estado.markAsTouched({ onlySelf: true });
      this.fechaInicio.markAsTouched({ onlySelf: true });
      this.fechaCierre.markAsTouched({ onlySelf: true });*/
    }
  }
}
