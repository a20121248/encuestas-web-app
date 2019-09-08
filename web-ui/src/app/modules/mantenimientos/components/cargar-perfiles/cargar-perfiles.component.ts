import { Component, OnInit, OnDestroy } from '@angular/core';
import { PerfilService } from 'src/app/shared/services/perfil.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-perfiles',
  templateUrl: './cargar-perfiles.component.html',
  styleUrls: ['./cargar-perfiles.component.scss']
})
export class CargarPerfilesComponent implements OnInit, OnDestroy {
  titulo: string;
  cantPerfiles: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private perfilService: PerfilService) {
    this.titulo = '5. CARGAR PERFILES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.cantPerfiles = 0;
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
    this.subscribeCantidad = this.perfilService.count().subscribe(cantPerfiles => {
      this.cantPerfiles = cantPerfiles;
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
      swal.fire('Cargar perfiles', 'Por favor, seleccione un perfil.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.subscribeSubir = this.perfilService.upload(formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar perfiles`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Perfiles.xlsx';
    this.perfilService.download().subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
