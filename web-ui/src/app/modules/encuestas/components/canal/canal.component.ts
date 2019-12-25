import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, AbstractControl, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-canal',
  templateUrl: './canal.component.html',
  styleUrls: ['./canal.component.scss']
})
export class CanalComponent implements OnInit {

  @Input() lineaSeleccionada: ObjetoObjetos;
  @Input() haGuardado: boolean;

  @Output() estadoFormCanalToParent = new EventEmitter();
  //dcLinea = ['codigo', 'nombre', 'porcentaje', 'estado', 'ir'];
  dcLinea = ['codigo', 'nombre', 'porcentaje', 'ir'];
  groupForm: FormGroup;
  porcTotal: number;
  url: string;

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

  validacionItemControl(value: string): AbstractControl {
    return this.groupForm.get(String(value));
  }

  getTotalPorcentaje() {
    if (this.lineaSeleccionada.lstObjetos != null) {
      this.porcTotal = Math.round(this.lineaSeleccionada.lstObjetos.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.estadoFormCanalToParent.emit(value);
  }

  onChanges():void{
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
      this.sharedFormService.actualizarPorcentajeForm3(this.porcTotal);
    });
    this.sharedFormService.actualizarEstadoForm3(this.groupForm);
  }

  verificarLista(): boolean {
    if (this.lineaSeleccionada.lstObjetos != null) { //Verifica la carga de informacion desde el Parent
      for (let canal of this.lineaSeleccionada.lstObjetos.map(t => t)) {
        let control: FormControl = new FormControl(canal.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(canal.codigo), control);
      }
      return true;
    } else {
      return false;
    }
  }

  revisarEdicionFormulario(lineaID: number, canalID: number){
    let form1valid:boolean;
    let form1dirty: boolean;
    let form1pristine: boolean;
    let form2valid:boolean;
    let form2dirty: boolean;
    let form2pristine: boolean;
    let form3dirty: boolean;
    let form3pristine: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
      form1pristine = data.pristine;
      form1valid = data.valid;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
      form2pristine = data.pristine;
      form2valid = data.valid;
    });
    this.sharedFormService.form3Actual.subscribe(data => {
      form3dirty = data.dirty;
      form3pristine = data.pristine;
    });
    this.url = lineaID+"/"+canalID+"/producto-subcanal";    
    // Valida si se haGuardado y las formas son validas, or si no se ha cambiado alguna forma y si son validas
    if((this.haGuardado && form1pristine && form2pristine && form3pristine) || (this.groupForm.valid && !form3dirty && form1valid && !form1dirty && form2valid && !form2dirty)){
      this.router.navigate([this.url], { relativeTo: this.route });
    } else {
      // Valida si alguna de las formas ha sido cambiada
      if((form1dirty) || (form2dirty)  || (form3dirty)){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      }
    }
  }
}
