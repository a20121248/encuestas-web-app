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
  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalCrearComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({});
    this.data.camposArr.forEach((campos: Campo[]) => {
      campos.forEach((campo: Campo) => {
        this.formGroup.addControl(campo.name, new FormControl(campo.value, Validators.required));
      });
    });
  }

  submit() {
    if (this.formGroup.valid) {
      this.dialogRef.close(this.formGroup.value);
    } else {
      this.formGroup.markAllAsTouched();
    }
  }
}
