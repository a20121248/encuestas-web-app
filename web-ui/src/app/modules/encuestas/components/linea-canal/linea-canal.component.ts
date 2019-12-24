import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';

import { Linea } from 'src/app/shared/models/linea';
import { LineaService } from 'src/app/shared/services/linea.service';
import { HttpClient } from '@angular/common/http';
import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { Usuario } from 'src/app/shared/models/usuario';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-linea-canal',
  templateUrl: './linea-canal.component.html',
  styleUrls: ['./linea-canal.component.scss']
})
export class LineaCanalComponent implements OnInit {
  @Input() lstLineaCanales: ObjetoObjetos[];
  @Input() haGuardado: boolean;

  @Output() estadoFormLineaToParent = new EventEmitter();
  @Output() sendLinea = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();
  selectedElement: ObjetoObjetos | null;

  //dcLinea = ['codigo', 'nombre', 'porcentaje', 'estado'];
  dcLinea = ['codigo', 'nombre', 'porcentaje'];
  groupForm: FormGroup;
  porcTotal: number;
  lineasCompletas: boolean[];

  constructor(
    private lineaService: LineaService,
    private http: HttpClient,
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.onChanges();
  }

  validacionItemControl(value:string):AbstractControl{
    return this.groupForm.get(String(value));
  }

  getTotalPorcentaje() {
    if (this.lstLineaCanales != null) {
      this.porcTotal = Math.round(this.lstLineaCanales.map(t => 100*t.objeto.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  showCanalesBylineaBoton(objeto: ObjetoObjetos) {
    let porcentajeTotalCanales: number;
    let formCanalesValido: boolean;
    let datoValido: boolean;
    this.sharedFormService.form3PorcentajeActual.subscribe(data => {
      porcentajeTotalCanales = data;
    });
    this.sharedFormService.form3Actual.subscribe(data =>{
      if(data == null) {
        formCanalesValido = true;
      } else {
        formCanalesValido = data.valid;
      }
    });

    datoValido = this.validacionItemControl(objeto.objeto.codigo).valid;
    if (objeto.objeto.porcentaje == 0) {
      objeto.lstObjetos.map(t => {
        t.porcentaje = 0;
      });
    }
    if (datoValido && (porcentajeTotalCanales == 100 || porcentajeTotalCanales == -1) && formCanalesValido){
      this.sendLinea.emit(objeto);
      this.selectedElement = objeto;
    }
  }

  sendEstado(value: boolean) {
    this.estadoFormLineaToParent.emit(value);
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }

  onChanges():void{
    let completo: boolean;
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        completo = this.validatePorcentajesCompleto();
        this.sendEstado(completo);
      } else {
        completo = false;
        this.sendEstado(false);
      }
    });
    this.sharedFormService.actualizarEstadoCompletoForm1(completo);
    this.sharedFormService.actualizarEstadoForm1(this.groupForm);
  }

  validatePorcentajesCompleto():boolean{
    this.lineasCompletas=[];
    let porcCanalesByLinea: number;
    for(let linea of this.lstLineaCanales.map(t => t)){
      if(linea.objeto.porcentaje>0){
        porcCanalesByLinea = linea.lstObjetos.map(t=> t.porcentaje).reduce((acc, value) => acc + value, 0);
        if(porcCanalesByLinea != 100){
          this.lineasCompletas.push(false);
        } else {
          this.lineasCompletas.push(true);
        }
      }
    }
    // Valida si los canales de la linea con porcentaje>0 es igual a 100
    if(this.lineasCompletas.filter((item) => item == false).length > 0){
      return false;
    } else {
      return true;
    }
  }

  verificarLista(): boolean {
    if (this.lstLineaCanales != null) { //Verifica la carga de informacion desde el Parent
      for (let linea of this.lstLineaCanales.map(t => t)) {
        let control: FormControl = new FormControl(linea.objeto.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(linea.objeto.codigo), control);
      }
      return true;
    } else {
      return false;
    }
  }
}
