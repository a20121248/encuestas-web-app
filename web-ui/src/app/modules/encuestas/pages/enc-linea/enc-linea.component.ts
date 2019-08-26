import { Component, OnInit, ElementRef, Renderer2 } from "@angular/core";
import { ViewChild } from "@angular/core";
import { Location } from "@angular/common";
import swal from "sweetalert2";
import { Router, ActivatedRoute } from "@angular/router";
import { Title } from "@angular/platform-browser";

//-------------------COMPONENTES LOCALES----------------------------------

import { Linea } from "src/app/shared/models/linea";
import { LineaComponent } from "src/app/modules/encuestas/components/linea/linea.component";
import { LineaService } from "src/app/shared/services/linea.service";
import { JustificacionComponent } from "src/app/shared/components/justificacion/justificacion.component";
import { Encuesta } from "src/app/shared/models/encuesta";
import { UsuarioDatosComponent } from "src/app/shared/components/usuario-datos/usuario-datos.component";
import { Justificacion } from "src/app/shared/models/justificacion";
import { UsuarioService } from "src/app/shared/services/usuario.service";
import { Usuario } from "src/app/shared/models/usuario";
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: "app-enc-linea",
  templateUrl: "./enc-linea.component.html",
  styleUrls: ["./enc-linea.component.scss"]
})
export class EncLineaComponent implements OnInit {
  lstLineas: Linea[];
  observaciones: string;
  justificacion: Justificacion;

  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  encuesta: Encuesta;

  usuarioSeleccionado: Usuario;

  estadoLineas: boolean;
  estadoJustificacion: boolean;
  haGuardado: boolean;
  habilitarButton: boolean = false;

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
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
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

  estadoFormJustificacion(value: boolean) {
    this.estadoJustificacion = value;
    this.setButtonGuardar();
  }

  estadoFormLineas(value: boolean) {
    this.estadoLineas = value;
    this.setButtonGuardar();
  }

  setButtonGuardar() {
    if (this.estadoLineas && this.estadoJustificacion) {
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  goBack() {
    let form1dirty: boolean;
    let form2dirty: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
    });
    console.log(form1dirty);
    console.log(form2dirty);
    if (this.haGuardado) {
      this.location.back();
    } else {
      if (form1dirty || form2dirty) {
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      } else {
        if (!form1dirty && !form2dirty) {
          this.location.back();
        }
      }
    }
  }

  guardarEncuesta() {
    this.haGuardado = true;
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.lineaComponent.lstLineas;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    if (this.encuesta.justificacion.id != 5) {
      this.encuesta.justificacion.detalle = null;
    }
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.lineaService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }
}
