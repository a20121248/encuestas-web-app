import { Component, OnInit, Input } from '@angular/core';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-cargar-usuarios',
  templateUrl: './cargar-usuarios.component.html',
  styleUrls: ['./cargar-usuarios.component.scss']
})
export class CargarUsuariosComponent implements OnInit {
  titulo: string;
  cantUsuarios: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;

  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService) {
    this.titulo = 'CARGAR USUARIOS';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.usuarioService.count().subscribe(cantUsuarios => {
      this.cantUsuarios = cantUsuarios;
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
    this.usuarioService.upload(formData).subscribe(
      (res) => {
        this.porcentaje = res.porcentaje * 100;
        this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
      },
      (err) => this.error = err
    );
  }

  descargar(): void {
  }
}
