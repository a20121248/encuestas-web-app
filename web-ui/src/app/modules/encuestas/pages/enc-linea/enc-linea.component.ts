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
import { UsuarioService } from "src/app/shared/services/usuario.service";
import { Title } from "@angular/platform-browser";
import { Usuario } from "src/app/shared/models/usuario";

@Component({
  selector: "app-enc-linea",
  templateUrl: "./enc-linea.component.html",
  styleUrls: ["./enc-linea.component.css"]
})
export class EncLineaComponent implements OnInit {
  lstLineas: Linea[];
  observaciones: string;
  justificacion: Justificacion;

  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  encuesta: Encuesta;

  lineaSeleccionada: Linea;
  usuarioSeleccionado: Usuario;

  @ViewChild(LineaComponent, { static: false })
  lineaComponent: LineaComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private lineaService: LineaService,
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService
      .getUsuarioByPosicionCodigo(this.posicionCodigo)
      .subscribe(usuario => {
        this.usuarioSeleccionado = usuario;
        this.lineaService
          .obtenerEncuesta(this.usuarioSeleccionado)
          .subscribe(encuesta => {
            this.lstLineas = encuesta.lstItems as Linea[];
            this.observaciones = encuesta.observaciones;
            this.justificacion = encuesta.justificacion;
          });
      });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Línea');
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.lineaComponent.lstLineas;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.lineaService
      .guardarEncuesta(this.encuesta, this.usuarioSeleccionado)
      .subscribe(response => console.log(response), err => console.log(err));
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }

  goBack() {
    this.location.back();
  }
  // Output events
  showCanalesByLinea(linea: Linea) {
    this.lineaSeleccionada = linea;
  }
}
