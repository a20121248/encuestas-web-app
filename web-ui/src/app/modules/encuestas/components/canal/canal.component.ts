import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { FormGroup, AbstractControl, FormControl, Validators } from '@angular/forms';

import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: "app-form-canal",
  templateUrl: "./canal.component.html",
  styleUrls: ["./canal.component.css"]
})
export class CanalComponent implements OnInit {

  @Input() lineaSeleccionada: ObjetoObjetos;
  @Input() haGuardado: boolean;

  @Output() estadoFormCanalToParent = new EventEmitter();
  dcLinea = ["codigo", "nombre", "porcentaje","cumplimentar"];
  groupForm: FormGroup;
  porcTotal: number;
  
  constructor(
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    console.log(this.lineaSeleccionada);
    this.onChanges();
  }

  validacionItemControl(value:string):AbstractControl{
    return this.groupForm.get(String(value));
  }

  getTotalPorcentaje() {
    if (this.lineaSeleccionada.lstObjetos != null) {
      this.porcTotal = this.lineaSeleccionada.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
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
      this.sharedFormService.actualizarEstadoForm2(this.groupForm);
      this.sharedFormService.actualizarPorcentajeForm2(this.porcTotal);
    });
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
}
