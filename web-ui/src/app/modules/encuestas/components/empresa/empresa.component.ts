import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';
import { FormGroup, FormControl, Validators, Form, FormBuilder } from '@angular/forms';
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
  sumaValidada: boolean;
  valNegativos: boolean;
  groupFormEmpresas: FormGroup;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder) {
      this.groupFormEmpresas = new FormGroup({});
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

  onChanges():void{
    this.groupFormEmpresas.valueChanges
    .subscribe(data =>{
      if(this.groupFormEmpresas.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
    });
  }

  verificarlstEmpresas(): boolean {
    if (this.lstEmpresas != null) {
      for (let empresa of this.lstEmpresas.map(t => t)) {
        let control: FormControl = new FormControl(null, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo]));
        this.groupFormEmpresas.addControl(String(empresa.id), control);
      }
      return true;
    } else {
      return false;
    }
  }
  
}
