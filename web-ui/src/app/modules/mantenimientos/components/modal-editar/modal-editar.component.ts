import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-modal-editar',
  templateUrl: './modal-editar.component.html',
  styleUrls: ['./modal-editar.component.scss']
})
export class ModalEditarComponent implements OnInit {
  formGroup: FormGroup;
  titulo: string;
  proceso: Proceso;

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<ModalEditarComponent>,
              @Inject(MAT_DIALOG_DATA) private data) { }

  ngOnInit() {
    this.titulo = this.data.titulo;
    this.proceso = this.data.proceso;
    this.formGroup = this.formBuilder.group({
      id: this.proceso.id,
      codigo: this.proceso.codigo,
      nombre: this.proceso.nombre,
      usuario: this.proceso.usuario,
      fechaCierre: this.proceso.fechaCierre,
      fechaCreacion: this.proceso.fechaCreacion,
      fechaActualizacion: this.proceso.fechaActualizacion
    });
  }

  submit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
