import { Component, OnInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';
import * as fileSaver from 'file-saver';

import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { UsuarioDatosComponent } from 'src/app/shared/components/usuario-datos/usuario-datos.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Title } from '@angular/platform-browser';
import { ProductoCanalService } from 'src/app/shared/services/producto-canal.service';
import { ProductoCanalComponent } from 'src/app/modules/encuestas/components/producto-canal/producto-canal.component';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-enc-producto-canal',
  templateUrl: './enc-producto-canal.component.html',
  styleUrls: ['./enc-producto-canal.component.scss']
})
export class EncProductoCanalComponent implements OnInit {
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaId: string;

  estadoProductoCanal: boolean;
  haGuardado: boolean;
  habilitarButton: boolean = false;

  @ViewChild(ProductoCanalComponent, { static: false })
  productoCanalComponent: ProductoCanalComponent;
  @ViewChild(UsuarioDatosComponent, { static: false })
  usuarioDatosComponent: UsuarioDatosComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productoCanalService: ProductoCanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.lineaId = this.activatedRoute.snapshot.paramMap.get('lineaId');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.productoCanalService.obtenerEncuesta(this.posicionCodigo,
                                                this.usuarioSeleccionado.posicion.perfil.id,
                                                this.lineaId).subscribe(encuesta => {
        this.encuesta = encuesta;
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Producto - Canal');
  }

  estadoFormProductoCanal(value: boolean) {
    this.estadoProductoCanal = value;
    this.setButtonGuardar();
  }

  setButtonGuardar() {
    if (this.estadoProductoCanal) {
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  goBack() {
    let form1dirty: boolean;
    let form1pristine: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
      form1pristine = data.pristine;
    });
    if (this.haGuardado  && form1pristine) {
      this.location.back();
    } else {
      if (form1dirty) {
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      } else {
        if (!form1dirty) {
          this.location.back();
        }
      }
    }
  }

  guardarEncuesta() {
    let form1: FormGroup;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1 = data;
      form1.markAsPristine({onlySelf:true});
    });
    this.haGuardado = true;
    this.productoCanalService.guardarEncuesta(this.encuesta, this.posicionCodigo, this.lineaId).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
    this.sharedFormService.actualizarEstadoForm1(form1);
  }

  descargarEncuesta(): void {
    const filename = `${this.usuarioSeleccionado.codigo} - Encuesta de productos y canales.xlsx`;
    this.productoCanalService.downloadEncuesta(this.usuarioSeleccionado, this.lineaId).subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
  }
}

