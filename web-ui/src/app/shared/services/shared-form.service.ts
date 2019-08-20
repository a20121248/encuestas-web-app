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

  form1Actual = this.form1.asObservable();
  form2Actual = this.form2.asObservable();

  actualizarEstadoForm1(formActualizado: FormGroup){
    this.form1.next(formActualizado);
  }
  actualizarEstadoForm2(formActualizado: FormGroup){
    this.form2.next(formActualizado);
  }
}
