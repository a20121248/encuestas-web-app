import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PerfilService } from 'src/app/shared/services/perfil.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-cargar-perfiles',
  templateUrl: './cargar-perfiles.component.html',
  styleUrls: ['./cargar-perfiles.component.scss']
})
export class CargarPerfilesComponent implements OnInit {
  titulo: string;
  cantPerfiles: number;
  ruta: string;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;
  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private perfilService: PerfilService) {
    this.titulo = 'CARGAR PERFILES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.perfilService.count().subscribe(cantPerfiles => {
      this.cantPerfiles = cantPerfiles;
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
    this.perfilService.upload(formData).subscribe(
      (res) => {
        this.porcentaje = res.porcentaje * 100;
        this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
      },
      (err) => this.error = err
    );
  }

  descargar(): void {
    const filename = 'Perfiles.xlsx';
    this.perfilService.download().subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
    }, err => {
      console.log(err);
    });
  }
}
