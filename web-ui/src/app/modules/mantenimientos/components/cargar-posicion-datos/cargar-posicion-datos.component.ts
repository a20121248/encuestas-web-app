import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-posicion-datos',
  templateUrl: './cargar-posicion-datos.component.html',
  styleUrls: ['./cargar-posicion-datos.component.scss']
})
export class CargarPosicionDatosComponent implements OnInit {
  titulo: string;
  cantPosiciones: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;

  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private posicionService: PosicionService) {
    this.titulo = 'DATOS DE LAS POSICIONES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.posicionService.count().subscribe(cantPosiciones => {
      this.cantPosiciones = cantPosiciones;
    });
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      archivo: ['']
    });
  }

  get archivo() { return this.formGroup.get('archivo'); }

  seleccionarArchivo(e) {
    const rutaArr = e.target.value.split('\\');
    this.selectedFile = e.target.files.item(0);
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = this.selectedFile.size / 1024;
    this.ruta = rutaArr[rutaArr.length - 1];
  }

  subir(): void {
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.posicionService.uploadDatos(this.selectedProceso.id, formData).subscribe(
      (res) => {
        this.porcentaje = res.porcentaje * 100;
        this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
      },
      (err) => this.error = err
    );
  }

  descargar(): void {
    const filename = 'Datos de las posiciones.xlsx';
    this.posicionService.downloadDatos(this.selectedProceso.id).subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
    }, err => {
      console.log(err);
    });
  }

  eliminar(): void {
    swal.fire({
      title: `Eliminar parametría de la encuesta '${this.selectedProceso.codigo}'`,
      text: 'Esta acción es irreversible.',
      type: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar'
    }).then((result) => {
      if (result.value) {
        this.posicionService.deleteDatos(this.selectedProceso).subscribe(response => {
          console.log(response);
        }, err => {
          console.log(err);
        });
        swal.fire(
          `Eliminar parametría del proceso ${this.selectedProceso.nombre}`,
          'La parametría ha sido eliminada.',
          'success'
        );
      }
    });
  }
}
