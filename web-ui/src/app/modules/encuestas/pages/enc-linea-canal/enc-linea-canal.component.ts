import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';

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

@Component({
  selector: 'app-enc-linea-canal',
  templateUrl: './enc-linea-canal.component.html',
  styleUrls: ['./enc-linea-canal.component.css']
})
export class EncLineaCanalComponent implements OnInit {
  lstLineaCanal: LineaCanal[];
  justificacion: Justificacion;
  observaciones: string;

  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaSeleccionada: LineaCanal;

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
    });
    this.lineaCanalService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
      this.lstLineaCanal = encuesta;
      console.log(this.lstLineaCanal);
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Línea - Canal');
  }

  guardarEncuesta() {
    
  }

  showCanalesByLinea(linea: LineaCanal) {
    this.lineaSeleccionada = linea;
  }

  goBack() {
    this.location.back();
  }
}
