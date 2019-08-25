import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-crear',
  templateUrl: './modal-crear.component.html',
  styleUrls: ['./modal-crear.component.scss']
})
export class ModalCrearComponent implements OnInit {
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalCrearComponent>,
              @Inject(MAT_DIALOG_DATA) private data) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      codigo: this.data ? this.data.titulo : '',
      nombre: '',
      fechaCierre: ''
    });
  }

  submit(form) {
    this.dialogRef.close(`${form.value.filename}`);
  }
}
