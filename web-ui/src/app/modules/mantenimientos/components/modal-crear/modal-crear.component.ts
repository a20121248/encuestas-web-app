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
  titulo: string;

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalCrearComponent>,
              @Inject(MAT_DIALOG_DATA) private data) { }

  ngOnInit() {
    this.titulo = 'Crear encuesta';
    this.formGroup = this.formBuilder.group({
      codigo: '',
      nombre: '',
      fechaCierre: ''
    });
  }

  submit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
