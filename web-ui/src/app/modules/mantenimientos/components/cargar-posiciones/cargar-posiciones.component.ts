import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-cargar-posiciones',
  templateUrl: './cargar-posiciones.component.html',
  styleUrls: ['./cargar-posiciones.component.scss']
})
export class CargarPosicionesComponent implements OnInit {
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
    this.titulo = 'CARGAR POSICIONES';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.posicionService.count().subscribe(cantPosiciones  => {
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
    this.posicionService.upload(formData).subscribe(
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
