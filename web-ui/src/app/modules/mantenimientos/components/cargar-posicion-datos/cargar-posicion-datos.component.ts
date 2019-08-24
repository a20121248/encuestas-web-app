import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PosicionService } from 'src/app/shared/services/posicion.service';

@Component({
  selector: 'app-cargar-posicion-datos',
  templateUrl: './cargar-posicion-datos.component.html',
  styleUrls: ['./cargar-posicion-datos.component.css']
})
export class CargarPosicionDatosComponent implements OnInit {
  titulo: string;
  cantPosiciones: number;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;

  constructor(private posicionService: PosicionService) {
    this.titulo = 'CARGAR DATOS DE LAS POSICIONES';
    this.posicionService.count().subscribe(cantPosiciones => {
      this.cantPosiciones = cantPosiciones;
    });
  }

  ngOnInit() {
  }

  seleccionarArchivo(e) {
    const rutaArr = e.target.value.split('\\');
    this.ruta = rutaArr[rutaArr.length - 1];
  }

}
