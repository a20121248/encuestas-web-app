import { Component, OnInit, Input, ElementRef, Renderer2 } from "@angular/core";
import { ViewChild } from "@angular/core";
import { Location } from "@angular/common";

import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

//-------------------COMPONENTES LOCALES----------------------------------

import { Centro } from 'src/app/shared/models/centro';
import { CentroComponent } from 'src/app/modules/encuestas/components/centro/centro.component';
import { CentroService } from 'src/app/shared/services/centro.service';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Title } from '@angular/platform-browser';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-enc-centro',
  templateUrl: './enc-centro.component.html',
  styleUrls: ['./enc-centro.component.scss']
})
export class EncCentroComponent implements OnInit {
  lstCentros: Centro[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  encuesta: Encuesta;
  usuarioSeleccionado: Usuario;
  estadoCentros: boolean;
  estadoJustificacion: boolean;
  haGuardado: boolean;
  habilitarButton: boolean = false;

  @ViewChild(CentroComponent, { static: false })
  centroComponent: CentroComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private centroService: CentroService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.centroService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.lstCentros = encuesta.lstItems as Centro[];
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Centros de costos');
  }

  // onChanges() {
  //   let validForm1: boolean;
  //   let validForm2: boolean;
  //   let completeForm1: number;
  //   this.sharedFormService.form1Actual
  //     .subscribe(data => {
  //       if(data == null) validForm1 = false;
  //       else validForm1 = data.valid;
  //     });
  //   this.sharedFormService.form2Actual
  //     .subscribe(data => {
  //       if(data == null) validForm2 = false;
  //       else validForm2 = data.valid;
  //     });
  //   this.sharedFormService.form1PorcentajeActual
  //   .subscribe( data => {
  //     completeForm1 = data;
  //   });
  //   console.log(validForm1 && validForm2 && completeForm1 == 100);
  //   if(validForm1 && validForm2 && completeForm1 == 100){
  //     if(this.btnGuardar != null) this.setButtonGuardar(true);
  //   } else {
  //     if(this.btnGuardar != null) this.setButtonGuardar(false);
  //   }
  // }
  estadoFormJustificacion(value: boolean) {
    this.estadoJustificacion = value;
    this.setButtonGuardar();
  }

  estadoFormCentros(value: boolean) {
    this.estadoCentros = value;
    this.setButtonGuardar();
  }

  setButtonGuardar(){
    if(this.estadoCentros && this.estadoJustificacion){
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  guardarEncuesta() {
    this.haGuardado = true;
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.centroComponent.lstCentros;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    if (this.encuesta.justificacion.id != 5) {
      this.encuesta.justificacion.detalle = null;
    }
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.centroService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
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
}
