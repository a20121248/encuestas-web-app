import { Component, OnInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { Location } from '@angular/common';
import swal from 'sweetalert2';

import { LineaCanalService } from 'src/app/shared/services/linea-canal.service';
import { LineaCanalComponent } from 'src/app/modules/encuestas/components/linea-canal/linea-canal.component';
import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { Linea } from 'src/app/shared/models/linea';
import { Canal } from 'src/app/shared/models/canal';
import { Title } from '@angular/platform-browser';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { ActivatedRoute } from '@angular/router';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Usuario } from 'src/app/shared/models/usuario';
import { CanalComponent } from 'src/app/modules/encuestas/components/canal/canal.component';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-enc-linea-canal',
  templateUrl: './enc-linea-canal.component.html',
  styleUrls: ['./enc-linea-canal.component.scss']
})
export class EncLineaCanalComponent implements OnInit {
  lstLineaCanales: ObjetoObjetos[];
  justificacion: Justificacion;
  observaciones: string;

  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaSeleccionada: ObjetoObjetos;
  porcentajePadre: boolean;
  estadoLineas: boolean;
  estadoLineasCompleto: boolean = false;
  estadoCanales:boolean;
  estadoJustificacion: boolean;
  haGuardado: boolean;
  private estadoButton = new BehaviorSubject<boolean>(false);
  estadoButtonActual =  this.estadoButton.asObservable();
  habilitarButton: boolean = false;

  @ViewChild(LineaCanalComponent, { static: false })
  lineaCanalComponent: LineaCanalComponent;
  @ViewChild(CanalComponent, { static: false })
  canalComponent: CanalComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private lineaCanalService: LineaCanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.lineaCanalService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.encuesta = encuesta;
        this.lstLineaCanales = (encuesta.lstItems as ObjetoObjetos[]);
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Línea - Canal');
  }

  estadoFormJustificacion(value: boolean) {
    this.estadoJustificacion = value;
    this.setButtonGuardar();
  }

  estadoFormLinea(value:boolean){
    this.estadoLineas = value;
    this.sharedFormService.form1EstadoCompletoActual.subscribe( data => {
      this.estadoLineasCompleto = data;
    });
    this.setButtonGuardar();
  }

  estadoFormCanal(value:boolean){
    this.estadoCanales = value;
    this.setButtonGuardar();
  }

  setButtonGuardar(){
    if((this.estadoLineas && this.estadoCanales && this.estadoJustificacion) || (this.estadoLineas && this.estadoLineasCompleto && this.estadoJustificacion)){
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  guardarEncuesta() {
    this.haGuardado = true;
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.lineaCanalComponent.lstLineaCanales;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.lineaCanalService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }

  showCanalesByLinea(objeto: ObjetoObjetos) {
    if(objeto.objeto.porcentaje==0){
      this.lineaSeleccionada = null;
      this.porcentajePadre = false;
      // this.setButtonGuardar();
    } else {
      this.lineaSeleccionada = objeto;
      this.porcentajePadre = true;
      // this.setButtonGuardar();
    }

  }

  goBack() {
    let form1dirty:boolean;
    let form2dirty:boolean;
    let form3dirty:boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
    });
    this.sharedFormService.form3Actual.subscribe(data => {
      form3dirty = data.dirty;
    });
    if(this.haGuardado){
      this.location.back();
    } else {
      if( form1dirty || form2dirty || form3dirty){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      } else {
        if( !form1dirty && !form2dirty && !form3dirty){
          this.location.back();
        }
      }
    }  
  }
}
