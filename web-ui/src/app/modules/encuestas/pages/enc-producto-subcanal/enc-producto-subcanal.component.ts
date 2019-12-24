import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import swal from 'sweetalert2';
import * as fileSaver from 'file-saver';

import { Encuesta } from 'src/app/shared/models/encuesta';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ProductoSubcanalService } from 'src/app/shared/services/producto-subcanal.service';
import { ProductoSubcanalComponent } from '../../components/producto-subcanal/producto-subcanal.component';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { Title } from '@angular/platform-browser';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-enc-producto-subcanal',
  templateUrl: './enc-producto-subcanal.component.html',
  styleUrls: ['./enc-producto-subcanal.component.scss']
})
export class EncProductoSubcanalComponent implements OnInit {
  lstItems: ObjetoObjetos[];
  titulo = 'Herramienta de encuestas';
  posicionCodigo: string;
  usuarioSeleccionado: Usuario;
  encuesta: Encuesta;
  lineaId: string;
  canalId: string;
  estadoCentros: boolean;
  haGuardado: boolean;
  habilitarButton: boolean = false;

  @ViewChild(ProductoSubcanalComponent, { static: false })
  productoSubcanalComponent: ProductoSubcanalComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private productoSubcanalService: ProductoSubcanalService,
    private location: Location,
    private usuarioService: UsuarioService,
    private titleService: Title,
    private renderer: Renderer2,
    private sharedFormService: SharedFormService
  ) {
    this.posicionCodigo = this.activatedRoute.snapshot.paramMap.get('codigo');
    this.lineaId = this.activatedRoute.snapshot.paramMap.get('lineaId');
    this.canalId = this.activatedRoute.snapshot.paramMap.get('canalId');
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      this.usuarioSeleccionado = usuario;
      this.productoSubcanalService.obtenerEncuesta(this.posicionCodigo, this.lineaId, this.canalId).subscribe(encuesta => {
        this.encuesta = encuesta;
        this.lstItems = encuesta.lstItems as ObjetoObjetos[];
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Productos - Subcanales');
  }

  estadoFormProductoSubcanal(value:boolean){
    this.estadoCentros = value;
    this.setButtonGuardar();
  }

  setButtonGuardar(){
    if(this.estadoCentros ){
      this.habilitarButton = true;
    } else {
      this.habilitarButton = false;
    }
  }

  guardarEncuesta() {
    let form1: FormGroup;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1 = data;
      form1.markAsPristine({onlySelf:true});
    });
    this.haGuardado = true;
    this.productoSubcanalService.guardarEncuesta(this.encuesta, this.posicionCodigo, this.lineaId, this.canalId).subscribe(
      response => console.log(response), err => console.log(err)
    );
    swal.fire('Guardar encuesta', 'Se guardó la encuesta.', 'success');
    this.sharedFormService.actualizarEstadoForm1(form1);
  }

  descargarEncuesta(): void {
    const filename = `${this.usuarioSeleccionado.codigo} - Encuesta de productos y subcanales.xlsx`;
    this.productoSubcanalService.downloadEncuesta(this.usuarioSeleccionado, this.lineaId, this.canalId).subscribe(
      res => {
        fileSaver.saveAs(new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), filename);
      }, err => {
        console.log(err);
      }
    );
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
      if( form1dirty ){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      } else {
        if( !form1dirty ){
          this.location.back();
        }
      }
    }
  }
}
