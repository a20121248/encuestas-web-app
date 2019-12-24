import { Component, OnInit, OnDestroy } from '@angular/core';
import { AreaService } from 'src/app/shared/services/area.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-areas',
  templateUrl: './cargar-areas.component.html',
  styleUrls: ['./cargar-areas.component.scss']
})
export class CargarAreasComponent implements OnInit, OnDestroy {
  titulo: string;
  cantAreas: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private areaService: AreaService) {
    this.titulo = '3. CARGAR ÁREAS';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.cantAreas = 0;
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
    this.subscribeCantidad = this.areaService.count().subscribe(cantAreas => {
      this.cantAreas = cantAreas;
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
      swal.fire('Cargar áreas', 'Por favor, seleccione un archivo.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.subscribeSubir = this.areaService.upload(formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar áreas`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Areas.xlsx';
    this.areaService.download().subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
