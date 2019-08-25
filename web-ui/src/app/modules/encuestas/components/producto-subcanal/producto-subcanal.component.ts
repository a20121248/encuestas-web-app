import { Component, OnInit, Input, ViewChild, ɵConsole, Output, EventEmitter } from '@angular/core';
import { Usuario } from 'src/app/shared/models/usuario';
import { ProductoSubcanal } from 'src/app/shared/models/producto-subcanal';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { FormGroup, AbstractControl, FormControl, Validators } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-producto-subcanal',
  templateUrl: './producto-subcanal.component.html',
  styleUrls: ['./producto-subcanal.component.scss']
})
export class ProductoSubcanalComponent implements OnInit {
  @Input() lstProductoSubcanales: ObjetoObjetos[];
  @Input() haGuardado:boolean;

  @Output() estadoFormProdSubCanToParent = new EventEmitter();

  lstCabeceraTableObtenida: string[];
  lstCabeceraTableDynamico: string[];
  sumaTotal: number;
  groupForm: FormGroup;
  porcTotal: number;

  constructor(
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.obtenerNombresColumna();
    this.onChanges();
  }

  obtenerNombresColumna() {
    if (this.lstProductoSubcanales != null && this.lstProductoSubcanales.length >0) {
      this.lstCabeceraTableObtenida = ['productos'];
      this.lstCabeceraTableDynamico = this.lstProductoSubcanales[0].lstObjetos.map(
        (subcanal) => {
          return subcanal.nombre;
        }
      );
      this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
      this.lstCabeceraTableObtenida.push('total');
    }
  }

  obtenerSuma(element: ObjetoObjetos): number {
    if (element != null) {
      return Math.trunc(100000 * element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0)) / 100000;
    }
    return 0;
  }

  obtenerSumaTotal(): number {
    this.sumaTotal = 0;
    if (this.lstProductoSubcanales != null) {
      this.lstProductoSubcanales.forEach(element => {
        this.sumaTotal += element.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      });
      this.porcTotal = Math.trunc(100000 * this.sumaTotal) / 100000;
      return this.porcTotal;
    } else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.estadoFormProdSubCanToParent.emit(value);
  }

  validacionItemControl(value:string):AbstractControl{
    return this.groupForm.get(String(value));
  }

  onChanges():void{
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
      this.sharedFormService.actualizarEstadoForm1(this.groupForm);
    });
  }

  verificarLista(): boolean {
    if (this.lstProductoSubcanales != null) { //Verifica la carga de informacion desde el Parent
      this.lstProductoSubcanales.forEach(producto => {
        for(let subcanal of producto.lstObjetos.map(t => t)){
          let control: FormControl = new FormControl(subcanal.porcentaje, Validators.compose([
            Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
            this.groupForm.addControl(producto.objeto.codigo+subcanal.codigo,control);
        }  
      });
      return true;
    } else {
      return false;
    }
  }
}
