import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import swal from 'sweetalert2';

import { Linea } from 'src/app/shared/models/linea';
import { LineaService } from 'src/app/shared/services/linea.service';
import { Usuario } from 'src/app/shared/models/usuario';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-linea',
  templateUrl: './linea.component.html',
  styleUrls: ['./linea.component.scss']
})
export class LineaComponent implements OnInit {
  @Input() lstLineas: Linea[];
  @Input() usuario: Usuario;
  @Input() haGuardado: boolean;

  @Output() estadoFormLineaToParent = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();
  //dcLinea = ['codigo', 'nombre', 'porcentaje', 'estado', 'ir'];
  dcLinea = ['codigo', 'nombre', 'porcentaje', 'ir'];
  url: string;
  porcTotal: number;
  groupForm: FormGroup;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.onChanges();
  }

  onChanges():void{
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
    });
    this.sharedFormService.actualizarEstadoForm1(this.groupForm);
  }

  sendEstado(value: boolean) {
    this.estadoFormLineaToParent.emit(value);
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }

  verificarLista(): boolean {
    if (this.lstLineas != null) { //Verifica la carga de informacion desde el Parent
      for (let linea of this.lstLineas.map(t => t)) {
        let control: FormControl = new FormControl(linea.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(linea.id), control);
      }
      return true;
    } else {
      return false;
    }
  }

  getTotalPorcentaje() {
    if (this.lstLineas != null) {
      this.porcTotal = Math.round(this.lstLineas.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  setRuta(lineaId:number){
    let perfilTipoId = this.usuario.posicion.perfil.tipo.id;
    if (perfilTipoId == 2) { // Es una Linea, ir a Producto-Canal
      this.url = lineaId + '/producto-canal';
    } else if (perfilTipoId == 3) { // Es un Canal, ir a Producto-Subcanal
      this.url = lineaId + `/12/producto-subcanal`;
    }
  }

  revisarEdicionFormulario(lineaId: number){
    this.setRuta(lineaId);
    let form1dirty: boolean;
    let form1pristine: boolean;
    let form2valid:boolean
    let form2dirty: boolean;
    let form2pristine: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
      form1pristine = data.pristine;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
      form2pristine = data.pristine;
      form2valid = data.valid;
    });
    if((this.haGuardado && form1pristine && form2pristine) || (this.groupForm.valid && !form1dirty && form2valid && !form2dirty)){
      this.router.navigate([this.url], { relativeTo: this.route });
    } else {
      if((form1dirty)|| (form2dirty)){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      }
    }
  }

  validacionItemControl(value:number):AbstractControl{
    return this.groupForm.get(String(value));
  }
}
