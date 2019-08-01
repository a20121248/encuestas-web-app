import { Component, OnInit } from '@angular/core';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { Producto } from 'src/app/shared/models/producto';

@Component({
  selector: 'app-enc-producto-subcanal',
  templateUrl: './enc-producto-subcanal.component.html',
  styleUrls: ['./enc-producto-subcanal.component.css']
})
export class EncProductoSubCanalComponent implements OnInit {
  lstProducto: Producto[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  encuesta: Encuesta;
  constructor() { }

  ngOnInit() {
  }

}
