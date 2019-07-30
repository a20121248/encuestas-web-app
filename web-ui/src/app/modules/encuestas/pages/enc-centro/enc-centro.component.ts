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
  titulo = "Herramienta de encuestas";

  constructor(
    private centroService: CentroService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}

  @ViewChild(CentroComponent, { static: false })
  centroComponent: CentroComponent;

  ngOnInit() {}

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.epsComponent.lstEps;
    this.encuesta.justificacion = null;
    this.encuesta.observaciones = null;
    this.epsService
      .guardarEncuesta(this.encuesta, this.posicionCodigo)
      .subscribe(response => console.log(response), err => console.log(err));

    // goBack() {
    //   this.location.back();
    // }
  }
  goBack() {
    this.location.back();
  }
}
