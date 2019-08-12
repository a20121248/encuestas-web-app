import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})

export class EmpresaComponent implements OnInit {
  @Input() lstEmpresas: Empresa[];
  @Input() usuario: Usuario;
  @Output() estado = new EventEmitter();
  dcEmpresa = ['nombre', 'porcentaje', 'ingresar'];
  url: string;
  porcTotal: number;
  validado: boolean;
  porcentajeControl: FormControl;
  rateControl: FormControl;

  formEmpresa = new FormGroup({
    noNegativo: new FormControl('', [CustomValidatorsService.validateNoNegativo])
  });
  // Select your input element.
  number = document.getElementById('number');


  constructor(
    private route: ActivatedRoute,
    private router: Router) {
    // this.rateControl = new FormControl("", [Validators.max(100), Validators.min(0)])
    // this.porcentajeControl =  new FormControl(10,[Validators.required]);

  }

  ngOnInit() {
  }
  restrictMinus(e) {
    var inputKeyCode = e.keyCode ? e.keyCode : e.which;

    if (inputKeyCode != null) {
      if (inputKeyCode == 45) e.preventDefault();
    }
  }
  getTotalPorcentaje() {
    if (this.lstEmpresas != null) {
      this.porcTotal = this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
      // this.lstEmpresas.map(t => )
      this.validado = CustomValidatorsService.validatePorcentajeTotal(this.porcTotal).completo;
      this.sendEstado(this.validado);
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      this.validado = CustomValidatorsService.validatePorcentajeTotal(this.porcTotal).completo;
      
      return this.porcTotal;
    }
  }

  sendEstado(value:boolean){
    this.estado.emit(value);
  }

  keyCode(event) {
    var x = event.keyCode;
    if (x == 109 || x == 189) {
      event.preventDefault();
      alert("You can't enter minus value !");
    }
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
