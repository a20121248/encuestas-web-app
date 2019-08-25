import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';

import { Justificacion } from 'src/app/shared/models/justificacion';
import { JustificacionService } from 'src/app/shared/services/justificacion.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';


export interface Justificacion {
  nombre: string;
  id: number;
}

@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.scss']
})
export class JustificacionComponent implements OnInit {
  lstJustificaciones: Justificacion[];
  @Input() observaciones: string;
  @Input() justificacion: Justificacion;
  myGroup: FormGroup;
  @Output() estadoFormJustToParent =  new EventEmitter();

  constructor(
    private justificacionService: JustificacionService,
    private sharedFormService: SharedFormService) {
      this.justificacionService.getJustificaciones().subscribe(justificaciones => {
        this.lstJustificaciones = justificaciones;
      });
      this.myGroup = new FormGroup({
        idJust: new FormControl('', Validators.compose([Validators.required,CustomValidatorsService.validarVacio])),
        detalleJust: new FormControl(null),
        obs: new FormControl('',Validators.compose([Validators.required]))
     });
    }

  ngOnInit() {
    this.onChangeOptions();
    this.onChangesValue();
  }

  get idJustControl(){
    return this.myGroup.get("idJust");
  }

  get detalleJustControl(){
    return this.myGroup.get("detalleJust");
  }

  get obsJustControl(){
    return this.myGroup.get("obs");
  }

  sendEstado(value: boolean) {
    this.estadoFormJustToParent.emit(value);
  }

  onChangesValue(): void {
    this.myGroup.valueChanges
    .subscribe(data =>{
      this.sendEstado(this.myGroup.valid);
      this.sharedFormService.actualizarEstadoForm2(this.myGroup);
    });
  }

  onChangeOptions():void {
    this.myGroup.get('idJust').valueChanges
    .subscribe(data =>{
      if(data != 5){
        this.myGroup.get('detalleJust').clearValidators();
        this.myGroup.get('detalleJust').setValue(null);
      } else {
        this.myGroup.get('detalleJust').setValidators(([Validators.required]));
      }
      this.myGroup.get('detalleJust').updateValueAndValidity();
    });
  }
}
