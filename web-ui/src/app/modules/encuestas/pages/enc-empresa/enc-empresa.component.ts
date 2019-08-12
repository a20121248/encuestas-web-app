import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';

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

@Component({
  selector: 'app-enc-empresa',
  templateUrl: './enc-empresa.component.html',
  styleUrls: ['./enc-empresa.component.css']
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
  estadoJustificion:boolean;
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
    private changeDetector: ChangeDetectorRef
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      console.log(usuario);
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

  stateFormJustificacion(){

  }

  stateFormEmpresas(value:boolean,){
    this.estadoEmpresas = value;
  }

  goBack() {
    this.location.back();
  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.empresaComponent.lstEmpresas;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    if (this.encuesta.justificacion.id != 5) {
      this.encuesta.justificacion.detalle = null;
    }
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    console.log(this.encuesta);
    this.empresaService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(response =>
      console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }
}
