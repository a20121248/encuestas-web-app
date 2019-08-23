import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import swal from 'sweetalert2';

import { Linea } from 'src/app/shared/models/linea';
import { LineaService } from 'src/app/shared/services/linea.service';
import { Usuario } from 'src/app/shared/models/usuario';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-linea',
  templateUrl: './linea.component.html',
  styleUrls: ['./linea.component.css']
})
export class LineaComponent implements OnInit {
  @Input() lstLineas: Linea[];
  @Input() usuario: Usuario;
  @Input() haGuardado: boolean;
  
  @Output() estadoFormLineaToParent = new EventEmitter();

  dcLinea = ['codigo', 'nombre', 'porcentaje', 'estado', 'ir'];
  url: string;
  porcTotal: number;
  groupForm: FormGroup;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.onChanges();
  }

  onChanges():void{
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
      this.sharedFormService.actualizarEstadoForm1(this.groupForm);
    });
  }

  sendEstado(value: boolean) {
    this.estadoFormLineaToParent.emit(value);
  }

  verificarLista(): boolean {
    if (this.lstLineas != null) { //Verifica la carga de informacion desde el Parent
      for (let linea of this.lstLineas.map(t => t)) {
        let control: FormControl = new FormControl(linea.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(linea.id), control);
      }
      return true;
    } else {
      return false;
    }
  }

  getTotalPorcentaje() {
    if (this.lstLineas != null) {
      this.porcTotal = this.lstLineas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }  

  setRuta(lineaId:number){
    let perfilTipoId = this.usuario.posicion.perfil.perfilTipo.id;
    if (perfilTipoId == 2) { // Es una Linea, ir a Producto-Canal
      this.url = lineaId + '/producto-canal';
    } else if (perfilTipoId == 3) { // Es un Canal, ir a Producto-Subcanal
      this.url = lineaId + `/12/producto-subcanal`;
    }
  }

  revisarEdicionFormulario(lineaId: number){
    this.setRuta(lineaId);
    let form2Valid:boolean;
    let form2Dirty:boolean;
    this.sharedFormService.form2Actual.subscribe( data => {
      form2Valid = data.valid;
      form2Dirty = data.dirty;
    } );
    if((this.haGuardado && this.groupForm.valid && form2Valid) || (this.groupForm.valid && !this.groupForm.dirty && form2Valid && !form2Dirty)){
      this.router.navigate([this.url], { relativeTo: this.route });
    } else {
      if((this.groupForm.valid && this.groupForm.dirty)|| (form2Valid && form2Dirty)){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      }
    }
  }

  validacionItemControl(value:number):AbstractControl{
    return this.groupForm.get(String(value));
  }
}
