import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';

import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Title } from '@angular/platform-browser';
import { ProductoCanalService } from 'src/app/shared/services/producto-canal.service';
import { ProductoCanalComponent } from 'src/app/modules/encuestas/components/producto-canal/producto-canal.component';
import { Encuesta } from 'src/app/shared/models/encuesta';

@Component({
  selector: 'app-enc-producto-canal',
  templateUrl: './enc-producto-canal.component.html',
  styleUrls: ['./enc-producto-canal.component.css']
})
export class EncProductoCanalComponent implements OnInit {
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaId: string;
  @ViewChild(ProductoCanalComponent, { static: false })
  productoCanalComponent: ProductoCanalComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productoCanalService: ProductoCanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.lineaId = this.activatedRoute.snapshot.paramMap.get('lineaId');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.productoCanalService.obtenerEncuesta(this.usuarioSeleccionado, this.lineaId).subscribe(encuesta => {
        this.encuesta = encuesta;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Producto - Canal');
  }

  goBack() {
    this.location.back();
  }

  guardarEncuesta() {
    this.productoCanalService.guardarEncuesta(this.encuesta, this.posicionCodigo).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
  }
}

