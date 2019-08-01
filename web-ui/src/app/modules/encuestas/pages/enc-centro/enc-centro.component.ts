import { Component, OnInit } from "@angular/core";
import { ViewChild } from "@angular/core";
import { Location } from "@angular/common";

import { Router, ActivatedRoute } from "@angular/router";
import swal from "sweetalert2";

//-------------------COMPONENTES LOCALES----------------------------------

import { Centro } from "../../../../shared/models/centro";
import { CentroComponent } from "../../components/centro/centro.component";
import { CentroService } from "src/app/shared/services/centro.service";
import { Encuesta } from "src/app/shared/models/encuesta";
import { UsuarioDatosComponent } from "src/app/shared/components/usuario-datos/usuario-datos.component";
import { Justificacion } from "src/app/shared/models/justificacion";
import { JustificacionComponent } from "src/app/shared/components/justificacion/justificacion.component";

@Component({
  selector: "app-enc-centro",
  templateUrl: "./enc-centro.component.html",
  styleUrls: ["./enc-centro.component.css"]
})
export class EncCentroComponent implements OnInit {
  lstCentros: Centro[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = "Herramienta de encuestas";
  posicionCodigo: string;
  encuesta: Encuesta;

  constructor(
    private centroService: CentroService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}

  @ViewChild(CentroComponent, { static: false })
  centroComponent: CentroComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get("codigo");
    this.centroService
      .obtenerEncuesta(this.posicionCodigo)
      .subscribe(encuesta => {
        this.lstCentros = encuesta.lstItems as Centro[];
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.centroComponent.lstCentros;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.centroService
      .guardarEncuesta(this.encuesta, this.posicionCodigo)
      .subscribe(response => console.log(response), err => console.log(err));
    swal.fire("Guardar encuesta", "Se guardó la encuesta.", "success");
  }

  goBack() {
    this.location.back();
  }
}
