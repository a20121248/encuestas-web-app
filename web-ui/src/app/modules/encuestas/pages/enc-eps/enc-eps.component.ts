import { Component, OnInit, ElementRef, Renderer2 } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';

import { Encuesta } from 'src/app/shared/models/encuesta';
import { Eps } from 'src/app/shared/models/eps';
import { EpsComponent } from 'src/app/modules/encuestas/components/eps/eps.component';
import { EpsService } from 'src/app/shared/services/eps.service';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Usuario } from 'src/app/shared/models/usuario';
import { Title } from '@angular/platform-browser';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-enc-eps',
  templateUrl: './enc-eps.component.html',
  styleUrls: ['./enc-eps.component.css']
})
export class EncEPSComponent implements OnInit {
  lstEps: Eps[];
  observaciones: string;
  justificacion: Justificacion;
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  estadoEps: boolean;
  haGuardado:boolean;
  habilitarButton: boolean = false;

  @ViewChild(EpsComponent, { static: false })
  epsComponent: EpsComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;
  @ViewChild("btnGuardar",{static: false}) 
  btnGuardar: ElementRef;
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private epsService: EpsService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.epsService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.lstEps = encuesta.lstItems as Eps[];
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Líneas EPS');
  }

  estadoFormEps(value:boolean){
    this.estadoEps = value;
    this.setButtonGuardar();
  }

  setButtonGuardar(){
    if(this.estadoEps){
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  guardarEncuesta() {
    this.haGuardado = true;
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.epsComponent.lstEps;
    this.epsService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }

  goBack() {
    let form1dirty:boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
    });
    if(this.haGuardado){
      this.location.back();
    } else {
      if(form1dirty){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      } else {
        if( !form1dirty){
          this.location.back();
        }
      }
    }
  }
}
