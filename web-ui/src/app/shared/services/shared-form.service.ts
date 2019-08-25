import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Usuario } from '../models/usuario';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class SharedFormService {
  private form1 = new BehaviorSubject<FormGroup>(null);
  private form2 = new BehaviorSubject<FormGroup>(null);
  private form1Porcentaje = new BehaviorSubject<number>(-1);
  private form2Porcentaje = new BehaviorSubject<number>(-1);

  form1Actual = this.form1.asObservable();
  form2Actual = this.form2.asObservable();
  form1PorcentajeActual = this.form1Porcentaje.asObservable();
  form2PorcentajeActual = this.form2Porcentaje.asObservable();

  actualizarEstadoForm1(formActualizado: FormGroup){
    this.form1.next(formActualizado);
  }
  actualizarEstadoForm2(formActualizado: FormGroup){
    this.form2.next(formActualizado);
  }

  actualizarPorcentajeForm1(porcentaje: number){
    this.form1Porcentaje.next(porcentaje);
  }

  actualizarPorcentajeForm2(porcentaje: number){
    this.form2Porcentaje.next(porcentaje);
  }
}
