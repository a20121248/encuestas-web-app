import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';

import { Encuesta } from 'src/app/shared/models/encuesta';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ProductoSubcanalService } from 'src/app/shared/services/producto-subcanal.service';
import { ProductoSubcanalComponent } from '../../components/producto-subcanal/producto-subcanal.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { UsuarioSeleccionadoService } from 'src/app/shared/services/usuario-seleccionado.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-enc-producto-subcanal',
  templateUrl: './enc-producto-subcanal.component.html',
  styleUrls: ['./enc-producto-subcanal.component.css']
})
export class EncProductoSubcanalComponent implements OnInit {
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaId: string;
  canalId: string;
  @ViewChild(ProductoSubcanalComponent, { static: false })
  productoSubcanalComponent: ProductoSubcanalComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productoSubcanalService: ProductoSubcanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.lineaId = this.activatedRoute.snapshot.paramMap.get('lineaId');
    this.canalId = this.activatedRoute.snapshot.paramMap.get('canalId');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.productoSubcanalService.obtenerEncuesta(this.usuarioSeleccionado, this.lineaId, this.canalId).subscribe(encuesta => {
        this.encuesta = encuesta;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Productos - Subcanales');
  }

  guardarEncuesta() {
    this.productoSubcanalService.guardarEncuesta(this.encuesta, this.usuarioSeleccionado).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }

  goBack() {
    this.location.back();
  }
}
