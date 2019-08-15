import { Injectable } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';


@Injectable({
  providedIn: 'root'
})
export class CustomValidatorsService extends Validators{

  static validarPatronPorcentaje(control:FormControl){
    const PTRN_PORCENTAJE = /^[\d]{0,3}[.,]{0,1}[\d]{0,2}$/;
    if(!PTRN_PORCENTAJE.test(control.value)){
      return {invalidNumber:true}
    } else {
      return null;
    }
  }

  static validarVacio(control:FormControl){
    if(control.value == 0 || control.value==null){
      return{vacio:true}
    } else {
       return null;
    }
  }

  static validatePorcentajeTotal(percnt: number){
    if(percnt == 100){
       return {completo:true};
    } else {
      return {completo:false};
    }
  }

  static validarNegativo(control:FormControl){
    if(control.value < 0){
      return {negativo:true}
    } else {
      return null;
    }
  }
  static validateNegativos(num: number[]){
    if(num.filter(x => x < 0).length >0){
       return {existNumNeg:true};
    } else {
      return {existNumNeg:false};
    }
  }
}