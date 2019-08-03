import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from "@angular/common";
import swal from "sweetalert2";

import { Encuesta } from 'src/app/shared/models/encuesta';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ProductoSubcanalService } from 'src/app/shared/services/producto-subcanal.service';
import { ProductoSubcanalComponent } from '../../components/producto-subcanal/producto-subcanal.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { UsuarioSeleccionadoService } from 'src/app/shared/services/usuario-seleccionado.service';

@Component({
  selector: 'app-enc-producto-subcanal',
  templateUrl: './enc-producto-subcanal.component.html',
  styleUrls: ['./enc-producto-subcanal.component.css']
})
export class EncProductoSubCanalComponent implements OnInit {
  matriz: ProductoSubcanal[];
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;

  constructor(
    private productoSubcanalService: ProductoSubcanalService,
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private usuarioService: UsuarioService,
    private usuarioSeleccionadoService: UsuarioSeleccionadoService
  ) { }

  @ViewChild(ProductoSubcanalComponent, { static: false })
  productoSubcanalComponent: ProductoSubcanalComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get("codigo");
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo)
      .subscribe(usr => {
        this.usuarioSeleccionado = usr;
        this.usuarioSeleccionadoService.setUsuarioSeleccionado(usr);
      });
    this.productoSubcanalService
      .obtenerMatriz(this.usuarioSeleccionado)
      .subscribe(matriz => {
        this.matriz = matriz;
      });
  }

  goBack() {
    this.location.back();
  }

  guardarEncuesta() {
    this.productoSubcanalService
      .guardarEncuesta(this.matriz, this.posicionCodigo)
      .subscribe(response => console.log(response), err => console.log(err));
    swal.fire("Guardar encuesta", "Se guardó la encuesta.", "success");
  }
}
