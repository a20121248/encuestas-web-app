import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-posicion-datos',
  templateUrl: './cargar-posicion-datos.component.html',
  styleUrls: ['./cargar-posicion-datos.component.scss']
})
export class CargarPosicionDatosComponent implements OnInit, OnDestroy {
  titulo: string;
  cantPosiciones: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  subscribeEliminar: Subscription;
  subscribeDescargar: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private posicionService: PosicionService) {
    this.titulo = '6. DATOS DE LAS POSICIONES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.cantPosiciones = 0;
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      archivo: ['']
    });
    this.obtenerCantidad();
  }

  ngOnDestroy(): void {
    if (this.subscribeSubir != null) {
      this.subscribeSubir.unsubscribe();
    }
    if (this.subscribeCantidad != null) {
      this.subscribeCantidad.unsubscribe();
    }
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  obtenerCantidad(): void {
    if (this.subscribeCantidad != null) {
      this.subscribeCantidad.unsubscribe();
    }
    this.subscribeCantidad = this.posicionService.countDatos(this.selectedProceso.id).subscribe(cantPosiciones => {
      this.cantPosiciones = cantPosiciones;
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

  onSelect(e): void {
    this.obtenerCantidad();
  }

  subir(): void {
    if (this.selectedProceso == null || this.selectedFile == null) {
      swal.fire('Cargar datos de las posiciones', 'Por favor, seleccione una encuesta y un archivo.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    if (this.subscribeSubir != null) {
      this.subscribeSubir.unsubscribe();
    }
    this.subscribeSubir = this.posicionService.uploadDatos(this.selectedProceso.id, formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar datos de las posiciones`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Datos de las posiciones.xlsx';
    this.posicionService.downloadDatos(this.selectedProceso.id).subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
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
        if (this.subscribeEliminar != null) {
          this.subscribeEliminar.unsubscribe();
        }
        this.subscribeEliminar = this.posicionService.deleteDatos(this.selectedProceso).subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          this.obtenerCantidad();
          swal.fire(`Eliminar parametría del proceso ${this.selectedProceso.nombre}`, 'La parametría ha sido eliminada.', 'success');
        });
      }
    });
  }
}
