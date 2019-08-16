import { Component, OnInit, ViewChild } from '@angular/core';
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
import { CanalComponent } from '../../components/canal/canal.component';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';

@Component({
  selector: 'app-enc-linea-canal',
  templateUrl: './enc-linea-canal.component.html',
  styleUrls: ['./enc-linea-canal.component.css']
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
    private titleService: Title
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.lineaCanalService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.encuesta = encuesta;
        this.lstLineaCanales = (encuesta.lstItems as ObjetoObjetos[]);
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Línea - Canal');
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.lineaCanalComponent.lstLineaCanales;
    this.lineaCanalService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }

  showCanalesByLinea(objeto: ObjetoObjetos) {
    if(objeto.objeto.porcentaje==0){
      this.lineaSeleccionada = null;
      this.porcentajePadre = false;
    } else {
      this.lineaSeleccionada = objeto;
      this.porcentajePadre = true;
    }
    
  }

  goBack() {
    this.location.back();
  }
}
