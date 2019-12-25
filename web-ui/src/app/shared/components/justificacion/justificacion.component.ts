import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';

import { Justificacion } from 'src/app/shared/models/justificacion';
import { JustificacionService } from 'src/app/shared/services/justificacion.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.scss']
})
export class JustificacionComponent implements OnInit {
  lstJustificaciones: Justificacion[];
  @Input() observaciones: string;
  @Input() justificacion: Justificacion;
  @Output() estadoFormJustToParent = new EventEmitter();
  justificacionGroup: FormGroup;

  constructor(
    private justificacionService: JustificacionService,
    private sharedFormService: SharedFormService) {
    }

  ngOnInit() {
    this.justificacionService.getJustificaciones().subscribe(justificaciones => {
      this.lstJustificaciones = justificaciones;
      this.justificacionGroup = new FormGroup({
        justificacionControl: new FormControl(this.justificacion.id != 0 ? this.lstJustificaciones[this.justificacion.id-1] : this.justificacion, Validators.compose([Validators.required,CustomValidatorsService.validarVacio])),
        detalleControl: new FormControl(this.justificacion.detalle),
        observacionesControl: new FormControl(this.observaciones)
      });
      this.onChangeOptions();
      this.onChangesValue();
    });
  }

  get justificacionControl() {
    return this.justificacionGroup.get("justificacionControl");
  }

  get detalleControl() {
    return this.justificacionGroup.get("detalleControl");
  }

  get observacionesControl() {
    return this.justificacionGroup.get("observacionesControl");
  }

  sendEstado(value: boolean) {
    this.estadoFormJustToParent.emit(value);
  }

  onChangesValue(): void {
    this.justificacionGroup.valueChanges.subscribe(data => {
      this.sendEstado(this.justificacionGroup.valid);
    });
    this.sharedFormService.actualizarEstadoForm2(this.justificacionGroup);
  }

  onChangeOptions():void {
    this.justificacionControl.valueChanges.subscribe(data => {
      if(data.nombre != 'OTROS') {
        this.detalleControl.clearValidators();
        this.detalleControl.setValue(null);
      } else {
        this.detalleControl.setValidators(([Validators.required]));
      }
      this.detalleControl.updateValueAndValidity();
    });
  }
}
