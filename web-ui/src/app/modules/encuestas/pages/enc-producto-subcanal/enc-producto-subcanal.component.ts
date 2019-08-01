import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";

import { Encuesta } from 'src/app/shared/models/encuesta';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ProductoSubcanalService } from 'src/app/shared/services/producto-subcanal.service';
import { ProductoSubcanalComponent } from '../../components/producto-subcanal/producto-subcanal.component';

@Component({
  selector: 'app-enc-producto-subcanal',
  templateUrl: './enc-producto-subcanal.component.html',
  styleUrls: ['./enc-producto-subcanal.component.css']
})
export class EncProductoSubCanalComponent implements OnInit {
  matriz: any[];
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  encuesta: Encuesta;
  constructor(
    private productoSubcanalService: ProductoSubcanalService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) { }
  
  @ViewChild(ProductoSubcanalComponent, {static: false})
  productoSubcanalComponent:ProductoSubcanalComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get("codigo");
    this.productoSubcanalService.obtenerMatriz()
    .subscribe(matriz => {
      this.matriz = matriz;
    });
  }

  goBack() {
    this.location.back();
  }
}
