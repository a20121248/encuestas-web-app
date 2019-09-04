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
  private form3 = new BehaviorSubject<FormGroup>(null);
  private form1Porcentaje = new BehaviorSubject<number>(-1);
  private form2Porcentaje = new BehaviorSubject<number>(-1);
  private form3Porcentaje = new BehaviorSubject<number>(-1);
  private form1EstadoCompleto = new BehaviorSubject<boolean>(false);

  form1Actual = this.form1.asObservable();
  form2Actual = this.form2.asObservable();
  form3Actual = this.form3.asObservable();
  form1PorcentajeActual = this.form1Porcentaje.asObservable();
  form2PorcentajeActual = this.form2Porcentaje.asObservable();
  form3PorcentajeActual = this.form3Porcentaje.asObservable();
  form1EstadoCompletoActual = this.form1EstadoCompleto.asObservable();

  actualizarEstadoForm1(formActualizado: FormGroup){
    this.form1.next(formActualizado);
  }
  actualizarEstadoForm2(formActualizado: FormGroup){
    this.form2.next(formActualizado);
  }
  actualizarEstadoForm3(formActualizado: FormGroup){
    this.form3.next(formActualizado);
  }

  actualizarPorcentajeForm1(porcentaje: number){
    this.form1Porcentaje.next(porcentaje);
  }
  actualizarPorcentajeForm2(porcentaje: number){
    this.form2Porcentaje.next(porcentaje);
  }
  actualizarPorcentajeForm3(porcentaje: number){
    this.form3Porcentaje.next(porcentaje);
  }

  actualizarEstadoCompletoForm1(estado: boolean){
    this.form1EstadoCompleto.next(estado);
  }
}
