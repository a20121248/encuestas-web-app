import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { CentroService } from 'src/app/shared/services/centro.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-cargar-centros',
  templateUrl: './cargar-centros.component.html',
  styleUrls: ['./cargar-centros.component.css']
})
export class CargarCentrosComponent implements OnInit {
  titulo: string;
  cantCentros: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;

  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private centroService: CentroService) {
    this.titulo = 'CARGAR CENTROS DE COSTOS';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.centroService.count().subscribe(cantCentros => {
      this.cantCentros = cantCentros;
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
    this.centroService.upload(formData).subscribe(
      (res) => {
        this.porcentaje = res.porcentaje * 100;
        this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
      },
      (err) => this.error = err
    );
  }
}