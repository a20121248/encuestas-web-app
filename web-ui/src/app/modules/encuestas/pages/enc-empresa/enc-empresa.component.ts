import { Component, OnInit, AfterViewInit, ViewChild, ViewChildren, QueryList } from '@angular/core';
import { Empresa } from 'src/app/shared/models/empresa';
import { EmpresaComponent } from 'src/app/modules/encuestas/components/empresa/empresa.component';
import { JustificacionComponent } from 'src/app/shared/components/justificacion/justificacion.component';
import { EmpresaService } from 'src/app/shared/services/empresa.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Justificacion } from 'src/app/shared/models/justificacion';

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
  encuesta: Encuesta;

  constructor(
    private empresaService: EmpresaService,
    private activatedRoute: ActivatedRoute) { }

  @ViewChild(EmpresaComponent, { static: false })
  empresaComponent: EmpresaComponent;
  @ViewChild(JustificacionComponent, { static: false })
  justificacionComponent: JustificacionComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  ngOnInit() {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.empresaService.obtenerEncuesta(this.posicionCodigo).subscribe(encuesta => {
      this.encuesta = encuesta;
      this.lstEmpresas = encuesta.lstItems as Empresa[];
      this.observaciones = encuesta.observaciones;
      this.justificacion = encuesta.justificacion;
    });
    /*this.empresaService.obtenerEncuesta(this.usuarioDatosComponent.usuario.posicion.codigo).subscribe(encuesta => {
      this.encuesta = encuesta;
    });
    console.log(this.encuesta);*/
  }

  ngAfterViewInit() {

  }

  guardarEncuesta() {
    this.encuesta = new Encuesta();
    this.encuesta.lstItems = this.empresaComponent.lstEmpresas;
    this.encuesta.justificacion = this.justificacionComponent.justificacion;
    this.encuesta.observaciones = this.justificacionComponent.observaciones;
    this.empresaService.guardarEncuesta(this.encuesta, this.usuarioDatosComponent.usuario.posicion.codigo).subscribe(
      response => console.log(response),
      err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }
}
