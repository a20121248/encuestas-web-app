import { Component, OnInit, OnDestroy } from '@angular/core';
import { CentroService } from 'src/app/shared/services/centro.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-centros',
  templateUrl: './cargar-centros.component.html',
  styleUrls: ['./cargar-centros.component.scss']
})
export class CargarCentrosComponent implements OnInit, OnDestroy {
  titulo: string;
  cantCentros: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private centroService: CentroService) {
    this.titulo = '4. CARGAR CENTROS';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.cantCentros = 0;
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
    this.subscribeCantidad = this.centroService.count().subscribe(cantCentros => {
      this.cantCentros = cantCentros;
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
      swal.fire('Cargar centros de costos', 'Por favor, seleccione un archivo.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.subscribeSubir = this.centroService.upload(formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar centros de costos`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Centros de costos.xlsx';
    this.centroService.download().subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
