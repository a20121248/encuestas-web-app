import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


//-------------------COMPONENTES LOCALES----------------------------------
import { Eps } from 'src/app/shared/models/eps';
import { FormGroup, AbstractControl, FormControl, Validators } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';



@Component({
  selector: 'app-form-eps',
  templateUrl: './eps.component.html',
  styleUrls: ['./eps.component.scss']
})
export class EpsComponent implements OnInit {
  @Input() lstEps: Eps[];
  @Output() sendestadoFormEpsToParent = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();
  dcEps = ['nombre', 'porcentaje'];
  porcTotal: number;
  groupForm: FormGroup;

  constructor(
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.onChanges();
  }

  getTotalPorcentaje() {
    if (this.lstEps != null) {
      this. porcTotal = Math.round(this.lstEps.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    } else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.sendestadoFormEpsToParent.emit(value);
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }

  validacionItemControl(value:number):AbstractControl{
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
    });
    this.sharedFormService.actualizarEstadoForm1(this.groupForm);
  }

  verificarLista(): boolean {
    if (this.lstEps != null) { //Verifica la carga de informacion desde el Parent
      for (let eps of this.lstEps.map(t => t)) {
        let control: FormControl = new FormControl(null, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(eps.id), control);
      }
      return true;
    } else {
      return false;
    }
  }
}
