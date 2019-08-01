import { Component, OnInit } from "@angular/core";
import { ViewChild } from "@angular/core";
import { Location } from "@angular/common";
import swal from "sweetalert2";
import { Router, ActivatedRoute } from "@angular/router";

//-------------------COMPONENTES LOCALES----------------------------------

import { Linea } from "../../../../shared/models/linea";
import { LineaComponent } from "../../components/linea/linea.component";
import { LineaService } from "src/app/shared/services/linea.service";
import { JustificacionComponent } from "src/app/shared/components/justificacion/justificacion.component";
import { Encuesta } from "src/app/shared/models/encuesta";
import { UsuarioDatosComponent } from "src/app/shared/components/usuario-datos/usuario-datos.component";
import { Justificacion } from "src/app/shared/models/justificacion";

@Component({
  selector: "app-enc-linea",
  templateUrl: "./enc-linea.component.html",
  styleUrls: ["./enc-linea.component.css"]
})
export class EncLineaComponent implements OnInit {
  lstLineas: Linea[];
  observaciones: string;
  justificacion: Justificacion;

  titulo = "Herramienta de encuestas";
  posicionCodigo: string;
  encuesta: Encuesta;

  lineaSeleccionada: Linea;

  constructor(
    private lineaService: LineaService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}

  @ViewChild(LineaComponent, { static: false })
  lineaComponent: LineaComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get("codigo");
    this.lineaService
      .obtenerEncuesta(this.posicionCodigo)
      .subscribe(encuesta => {
        this.lstLineas = encuesta.lstItems as Linea[];
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });
  }
  // Rest interaction
  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.lineaComponent.lstLineas;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.lineaService
      .guardarEncuesta(this.encuesta, this.posicionCodigo)
      .subscribe(response => console.log(response), err => console.log(err));
    swal.fire("Guardar encuesta", "Se guardó la encuesta.", "success");
  }

  goBack() {
    this.location.back();
  }
  // Output events
  showCanalesByLinea(linea: Linea) {
    this.lineaSeleccionada = linea;
  }
}
