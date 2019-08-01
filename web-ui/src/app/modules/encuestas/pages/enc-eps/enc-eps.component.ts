import { Component, OnInit } from "@angular/core";
import { ViewChild } from "@angular/core";
import { Location } from "@angular/common";
import swal from "sweetalert2";
import { Router, ActivatedRoute } from "@angular/router";

//-------------------COMPONENTES LOCALES----------------------------------

import { Eps } from "../../../../shared/models/eps";
import { EpsComponent } from "../../components/eps/eps.component";
import { EpsService } from "src/app/shared/services/eps.service";
import { JustificacionComponent } from "src/app/shared/components/justificacion/justificacion.component";
import { Encuesta } from "src/app/shared/models/encuesta";
import { UsuarioDatosComponent } from "src/app/shared/components/usuario-datos/usuario-datos.component";
import { Justificacion } from "src/app/shared/models/justificacion";

@Component({
  selector: "app-enc-eps",
  templateUrl: "./enc-eps.component.html",
  styleUrls: ["./enc-eps.component.css"]
})
export class EncEPSComponent implements OnInit {
  lstEps: Eps[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = "Herramienta de encuestas";
  posicionCodigo: string;
  encuesta: Encuesta;

  constructor(
    private epsService: EpsService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}

  @ViewChild(EpsComponent, { static: false })
  epsComponent: EpsComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get("codigo");
    this.epsService.obtenerEncuesta(this.posicionCodigo).subscribe(encuesta => {
      this.lstEps = encuesta.lstItems as Eps[];
      this.observaciones = null;
      this.justificacion = null;
    });
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.epsComponent.lstEps;
    this.encuesta.justificacion = null;
    this.encuesta.observaciones = null;
    this.epsService
      .guardarEncuesta(this.encuesta, this.posicionCodigo)
      .subscribe(response => console.log(response), err => console.log(err));
    swal.fire("Guardar encuesta", "Se guardó la encuesta.", "success");
  }

  goBack() {
    this.location.back();
  }
}
