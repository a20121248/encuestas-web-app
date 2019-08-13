import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Justificacion } from 'src/app/shared/models/justificacion';
import { JustificacionService } from 'src/app/shared/services/justificacion.service';
import { Router } from '@angular/router';
import { CustomValidatorsService } from '../../services/custom-validators.service';

export interface Justificacion {
  nombre: string;
  id: number;
}

@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.css']
})
export class JustificacionComponent implements OnInit {
  lstJustificaciones: Justificacion[];
  @Input() observaciones: string;
  @Input() justificacion: Justificacion;
  myGroup: FormGroup;
  @Output() sendEstadoFormJustificacionToParent =  new EventEmitter();
  
  constructor(
    private justificacionService: JustificacionService,
    private router: Router) {
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
    this.myGroup.get('idJust').valueChanges
    .subscribe(data =>{
      if(data == 5){
        this.myGroup.get('detalleJust').setValidators(([Validators.required]));
      } else {
        this.myGroup.get('detalleJust').clearValidators();
        this.myGroup.get('detalleJust').setValue('');
      }
      this.myGroup.updateValueAndValidity();
    });
    this.onChanges();
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
    this.sendEstadoFormJustificacionToParent.emit(value);
  }

  onChanges(): void {
    this.myGroup.valueChanges
    .subscribe(data =>{
      if(this.myGroup.valid){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
    });
  }

  select(justificacion){
    console.log(justificacion);
  }
}
