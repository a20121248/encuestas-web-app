import { Component, OnInit, OnDestroy } from '@angular/core';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as fileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-cargar-usuarios',
  templateUrl: './cargar-usuarios.component.html',
  styleUrls: ['./cargar-usuarios.component.scss']
})
export class CargarUsuariosComponent implements OnInit, OnDestroy {
  titulo: string;
  cantUsuarios: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  subscribeSubir: Subscription;
  subscribeCantidad: Subscription;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService) {
    this.titulo = '1. CARGAR COLABORADORES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.cantUsuarios = 0;
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
    this.subscribeCantidad = this.usuarioService.count().subscribe(cantUsuarios => {
      this.cantUsuarios = cantUsuarios;
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
      swal.fire('Cargar colaboradores', 'Por favor, seleccione un archivo.', 'error');
      return;
    }
    this.porcentaje = 0;
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.subscribeSubir = this.usuarioService.upload(formData).subscribe(
      (res) => {
        if (res != null && res.porcentaje != null) {
          this.porcentaje = res.porcentaje * 100;
          this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
        }
      }, (err) => {
        console.log(err);
        this.error = err;
      }, () => {
        this.obtenerCantidad();
        swal.fire(`Cargar usuarios`, 'Terminó la carga.', 'success');
      }
    );
  }

  descargar(): void {
    const filename = 'Colaboradores.xlsx';
    this.usuarioService.download().subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
