import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { AreaService } from 'src/app/shared/services/area.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-cargar-areas',
  templateUrl: './cargar-areas.component.html',
  styleUrls: ['./cargar-areas.component.scss']
})
export class CargarAreasComponent implements OnInit {
  titulo: string;
  cantAreas: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  formGroup: FormGroup;
  selectedFile: File;
  error: string;

  porcentaje: number;
  tamanhoCargado: number;
  tamanhoTotal: number;

  constructor(private formBuilder: FormBuilder, private areaService: AreaService) {
    this.titulo = 'CARGAR ÁREAS';
    this.porcentaje = 0;
    this.tamanhoCargado = 0;
    this.tamanhoTotal = 0;
    this.areaService.count().subscribe(cantAreas => {
      this.cantAreas = cantAreas;
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
    this.areaService.upload(formData).subscribe(
      (res) => {
        this.porcentaje = res.porcentaje * 100;
        this.tamanhoCargado = res.porcentaje * this.tamanhoTotal;
      },
      (err) => this.error = err
    );
  }
}
