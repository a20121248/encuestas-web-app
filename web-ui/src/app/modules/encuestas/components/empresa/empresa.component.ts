import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';
import { FormGroup, FormControl, Validators, Form } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { ThrowStmt } from '@angular/compiler';

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
  porcentajeControl: FormControl;
  myGroup: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router) {
    // this.myGroup = new FormGroup({
    //     id: new FormControl('',Validators.required),
    //     obs: new FormControl('',Validators.required)
    //  });
  }

  ngOnInit() {
  }

  getTotalPorcentaje() {
    if (this.lstEmpresas != null) {
      this.porcTotal = this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      this.valNegativos = CustomValidatorsService.validateNegativos(this.lstEmpresas.map(t => t.porcentaje)).existNumNeg;
      this.sumaValidada = CustomValidatorsService.validatePorcentajeTotal(this.porcTotal).completo;
      this.sendEstado((this.sumaValidada && !this.valNegativos));
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      this.valNegativos = true;
      this.sumaValidada = false;
      this.sendEstado((this.sumaValidada && !this.valNegativos));
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
}
