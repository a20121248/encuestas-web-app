import { Component, OnInit, ViewChild, ElementRef, Renderer2, ContentChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';
import * as fileSaver from 'file-saver';

import { Empresa } from 'src/app/shared/models/empresa';
import { EmpresaComponent } from 'src/app/modules/encuestas/components/empresa/empresa.component';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { EmpresaService } from 'src/app/shared/services/empresa.service';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Title } from '@angular/platform-browser';
import { FormGroup } from '@angular/forms';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-enc-empresa',
  templateUrl: './enc-empresa.component.html',
  styleUrls: ['./enc-empresa.component.scss']
})
export class EncEmpresaComponent implements OnInit {
  lstEmpresas: Empresa[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  estadoEmpresas: boolean;
  estadoJustificacion: boolean;
  haGuardado: boolean;
  habilitarButton: boolean = false;

  @ViewChild(EmpresaComponent, { static: false })
  empresaComponent: EmpresaComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private empresaService: EmpresaService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.empresaService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.lstEmpresas = encuesta.lstItems as Empresa[];
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Empresas');
  }

  estadoFormJustificacion(value: boolean) {
    this.estadoJustificacion = value;
    this.setButtonGuardar();
  }

  estadoFormEmpresas(value: boolean) {
    this.estadoEmpresas = value;
    this.setButtonGuardar();
  }

  setButtonGuardar() {
    if (this.estadoEmpresas && this.estadoJustificacion) {
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  goBack() {
    let form1dirty: boolean;
    let form1pristine: boolean;
    let form2dirty: boolean;
    let form2pristine: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
      form1pristine = data.pristine;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
      form2pristine = data.pristine;
    });
    if (this.haGuardado  && form1pristine && form2pristine) {
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
    let form1: FormGroup;
    let form2: FormGroup;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1 = data;
      form1.markAsPristine({onlySelf:true});
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2 = data;
      form2.markAsPristine({onlySelf:true});
    });
    this.haGuardado = true;
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.empresaComponent.lstEmpresas;
    this.encuesta.justificacion = this.justificacionComponent.justificacionControl.value;
    this.encuesta.justificacion.detalle = this.justificacionComponent.detalleControl.value;
    this.encuesta.observaciones = this.justificacionComponent.observacionesControl.value;
    this.empresaService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
    this.sharedFormService.actualizarEstadoForm1(form1);
    this.sharedFormService.actualizarEstadoForm2(form2);
  }

  descargarEncuesta(): void {
    const filename = `${this.usuarioSeleccionado.codigo} - Encuesta de empresas.xlsx`;
    this.empresaService.downloadEncuesta(this.usuarioSeleccionado).subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}
