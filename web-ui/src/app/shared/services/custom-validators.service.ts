import { Injectable } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';


@Injectable({
  providedIn: 'root'
})
export class CustomValidatorsService extends Validators{

  static validatePorcentajeTotal(percnt: number){
    if(percnt == 100){
       return {completo:true};
    } else {
      return {completo:false};
    }
  }

  static validateNoNegativo(control: FormControl){
    if(control.value >0){
       return {numberValid:true};
    } else {
      return {numberValid:false};
    }
  }
}