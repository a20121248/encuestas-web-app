import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators, Form, FormBuilder, AbstractControl } from '@angular/forms';
import swal from 'sweetalert2';

import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.scss']
})

export class EmpresaComponent implements OnInit {
  @Input() lstEmpresas: Empresa[];
  @Input() usuario: Usuario;
  @Input() haGuardado: boolean;

  @Output() estadoFormEmpresaToParent = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();
  //dcEmpresa = ['nombre', 'porcentaje', 'estado', 'ir'];
  dcEmpresa = ['nombre', 'porcentaje', 'ir'];
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

  getTotalPorcentaje() {
    if (this.lstEmpresas != null) {
      this.porcTotal = Math.round(this.lstEmpresas.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.estadoFormEmpresaToParent.emit(value);
  }

  revisarEmpresa(codigo: string): boolean {
    if (codigo == '1' || codigo == '2') {
      return true;
    }
    return false;
  }

  setRuta(codigo:string){
    let perfilTipoId = this.usuario.posicion.perfil.tipo.id;
    if (codigo == '1') {
      if (perfilTipoId == 1) { // Perfil STAFF: Pagina de centros de costos
        this.url = 'centro';
      } else if (perfilTipoId == 2) { // Perfil LINEA: Pagina de linea
        this.url = 'lineas';
      } else if ([3, 4].includes(perfilTipoId)) { // Perfil Canal o Mixto: Pagina de linea-canal
        this.url = 'linea-canal';
      }
    } else if (codigo == '2') {
      this.url = 'eps';
    }
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
    if (this.lstEmpresas != null) { //Verifica la carga de informacion desde el Parent
      for (let empresa of this.lstEmpresas.map(t => t)) {
        let control: FormControl = new FormControl(empresa.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(empresa.id), control);
      }
      return true;
    } else {
      return false;
    }
  }

  revisarEdicionFormulario(codigo: string){
    this.setRuta(codigo);
    let form1dirty: boolean;
    let form1pristine: boolean;
    let form2valid:boolean
    let form2dirty: boolean;
    let form2pristine: boolean;
    this.sharedFormService.form1Actual.subscribe(data => {
      form1dirty = data.dirty;
      form1pristine = data.pristine;
    });
    this.sharedFormService.form2Actual.subscribe(data => {
      form2dirty = data.dirty;
      form2pristine = data.pristine;
      form2valid = data.valid;
    });

    if((this.haGuardado && form1pristine && form2pristine) || (this.groupForm.valid && !form1dirty && form2valid && !form2dirty)){
      this.router.navigate([this.url], { relativeTo: this.route });
    } else {
      if((form1dirty)|| (form2dirty)){
        swal.fire({
          title: 'Cambios detectados',
          text: "Primero guarde antes de continuar.",
          type: "warning"
        });
      }
    }
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }
}
