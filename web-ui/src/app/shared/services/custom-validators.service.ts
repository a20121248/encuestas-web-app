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

  static validateNegativos(num: number[]){
    if(num.filter(x => x < 0).length >0){
       return {existNumNeg:true};
    } else {
      return {existNumNeg:false};
    }
  }
}