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

@Component({
  selector: 'app-enc-linea-canal',
  templateUrl: './enc-linea-canal.component.html',
  styleUrls: ['./enc-linea-canal.component.css']
})
export class EncLineaCanalComponent implements OnInit {
  lstLineaCanal: LineaCanal[];
  lstVertical: Linea[];
  lstHorizontal: Canal[];

  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  @ViewChild(LineaCanalComponent, { static: false })
  empresaComponent: LineaCanalComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private lineaCanalService: LineaCanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
  }

  @ViewChild(LineaCanalComponent, {static: false})  lineaCanalComponent: LineaCanalComponent ;

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Línea - Canal');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      /*this.lineaCanalService.obtenerEncuesta(this.usuarioSeleccionado).subscribe(encuesta => {
        this.lstEmpresas = encuesta.lstItems as Empresa[];
        this.observaciones = encuesta.observaciones;
        this.justificacion = encuesta.justificacion;
      });*/
    });
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.lstLineaCanal = this.lineaCanalComponent.getLstLineaCanal();
    this.lineaCanalService.postRespuesta (this.lstLineaCanal);
  }

  goBack() {
    this.location.back();
  }
}
