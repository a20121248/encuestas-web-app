import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { AreaService } from 'src/app/shared/services/area.service';

@Component({
  selector: 'app-cargar-areas',
  templateUrl: './cargar-areas.component.html',
  styleUrls: ['./cargar-areas.component.css']
})
export class CargarAreasComponent implements OnInit {
  titulo: string;
  cantAreas: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;

  constructor(private areaService: AreaService) {
    this.titulo = 'CARGAR ÁREAS';
    this.areaService.count().subscribe(cantAreas => {
      this.cantAreas = cantAreas;
    });
  }

  ngOnInit() {
  }

  seleccionarArchivo(e) {
    const rutaArr = e.target.value.split('\\');
    this.ruta = rutaArr[rutaArr.length - 1];
  }
}
