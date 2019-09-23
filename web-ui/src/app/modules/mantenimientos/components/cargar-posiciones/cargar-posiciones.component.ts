import { Component, OnInit, OnDestroy } from '@angular/core';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-posiciones',
  templateUrl: './cargar-posiciones.component.html',
  styleUrls: ['./cargar-posiciones.component.scss']
})
export class CargarPosicionesComponent implements OnInit, OnDestroy {
  titulo: string;
  cantPosiciones: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private posicionService: PosicionService) {
    this.titulo = '2. CARGAR POSICIONES';
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
  }

  obtenerCantidad(): void {
    this.subscribeCantidad = this.posicionService.count().subscribe(cantPosiciones  => {
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

  subir(): void {
    if (this.selectedFile == null) {
      swal.fire('Cargar posiciones', 'Por favor, seleccione un archivo.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.subscribeSubir = this.posicionService.upload(formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar posiciones`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Posiciones.xlsx';
    this.posicionService.download().subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
