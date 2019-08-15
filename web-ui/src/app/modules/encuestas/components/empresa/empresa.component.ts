import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';
import { FormGroup, FormControl, Validators, Form, FormBuilder, AbstractControl } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})

export class EmpresaComponent implements OnInit {
  @Input() lstEmpresas: Empresa[];
  @Input() usuario: Usuario;
  @Output() sendEstadoFormEmpresaToParent = new EventEmitter();
  dcEmpresa = ['nombre', 'porcentaje', 'ingresar'];
  url: string;
  porcTotal: number;
  groupForm: FormGroup;
  constructor() {
      this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.onChanges();
  }

  getTotalPorcentaje() {
    if (this.lstEmpresas != null) {
      this.porcTotal = this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.sendEstadoFormEmpresaToParent.emit(value);
  }

  revisarEmpresa(codigo: string): boolean {
    let perfilTipoId = this.usuario.posicion.perfil.perfilTipo.id;
    if (codigo == '1') {
      if (perfilTipoId == 1) { // Perfil STAFF: Pagina de centros de costos
        this.url = 'centro';
        return true;
      } else if (perfilTipoId == 2) { // Perfil LINEA: Pagina de linea
        this.url = 'lineas';
        return true;
      } else if ([3, 4].includes(perfilTipoId)) { // Perfil Canal o Mixto: Pagina de linea-canal
        this.url = 'linea-canal';
        return true;
      }
    } else if (codigo == '2') {
      this.url = 'eps';
      return true;
    }
    return false;
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
  }

  verificarLista(): boolean {
    if (this.lstEmpresas != null) { //Verifica la carga de informacion desde el Parent
      for (let empresa of this.lstEmpresas.map(t => t)) {
        let control: FormControl = new FormControl(null, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(empresa.id), control);
      }
      return true;
    } else {
      return false;
    }
  }
}
