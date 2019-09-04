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
      this.sharedFormService.actualizarEstadoForm3(this.groupForm);
      this.sharedFormService.actualizarPorcentajeForm3(this.porcTotal);
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

  revisarEdicionFormulario(lineaID: number, canalID: number){
    let form1Valid:boolean;
    let form1Dirty:boolean;
    let form2Valid:boolean;
    let form2Dirty:boolean;

    this.url = lineaID+"/"+canalID+"/producto-subcanal";
    console.log(this.url);
    this.sharedFormService.form1Actual.subscribe( data => {
      form1Valid = data.valid;
      form1Dirty = data.dirty;
    } );

    this.sharedFormService.form2Actual.subscribe( data => {
      form2Valid = data.valid;
      form2Dirty = data.dirty;
    } );
    // Valida si se haGuardado y las formas son validas, or si no se ha cambiado alguna forma y si son validas
    if((this.haGuardado && this.groupForm.valid && form1Valid && form2Valid) || (this.groupForm.valid && !this.groupForm.dirty && form1Valid && !form1Dirty && form2Valid && !form2Dirty)){
      this.router.navigate([this.url], { relativeTo: this.route });
    } else {
      // Valida si alguna de las formas ha sido cambiada
      if((this.groupForm.valid && this.groupForm.dirty) || (form1Valid && form1Dirty)  || (form2Valid && form2Dirty)){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      }
    }
  }
}
