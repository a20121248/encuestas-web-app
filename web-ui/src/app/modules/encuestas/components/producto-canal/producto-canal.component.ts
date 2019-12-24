import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { Usuario } from 'src/app/shared/models/usuario';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';
import { FormGroup, AbstractControl, FormControl, Validators } from '@angular/forms';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';


@Component({
  selector: 'app-form-producto-canal',
  templateUrl: './producto-canal.component.html',
  styleUrls: ['./producto-canal.component.scss']
})
export class ProductoCanalComponent implements OnInit {
  @Input() lstProductoCanales: ObjetoObjetos[];
  @Input() usuarioSeleccionado: Usuario;
  @Input() haGuardado:boolean;

  @Output() estadoFormProdCanToParent = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();
  lstCabeceraTableObtenida: string[];
  lstCabeceraTableDynamico: string[];
  sumaTotal: number;
  porcTotal: number;
  groupForm: FormGroup;

  constructor(
    private sharedFormService: SharedFormService
  ) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.obtenerNombresColumna();
    this.onChanges();
  }

  obtenerNombresColumna() {
    if (this.lstProductoCanales != null && this.lstProductoCanales.length >0 ) {
      this.lstCabeceraTableObtenida = ['productos'];
      this.lstCabeceraTableDynamico = this.lstProductoCanales[0].lstObjetos.map(
        (subcanal) => {
          return subcanal.nombre;
        }
      );
      this.lstCabeceraTableObtenida.push(...this.lstCabeceraTableDynamico);
      this.lstCabeceraTableObtenida.push('total');
    }
  }

  obtenerSumaByRow(element: any): number {
    if (element != null) {
      return Math.round(element.lstObjetos.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0)) / 100;
    }
    return 0;
  }

  obtenerSumaByColumn(column: number):number{
    if(this.lstProductoCanales!= null){
      return Math.round(this.lstProductoCanales.map(t => 100*t.lstObjetos[column].porcentaje).reduce((acc, value) => acc + value, 0))/100;
    }
    return 0;
  }

  obtenerSumaTotal(): number {
    this.sumaTotal = 0;
    if (this.lstProductoCanales != null) {
      this.lstProductoCanales.forEach(element => {
        this.sumaTotal += Math.round(element.lstObjetos.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
        this.sumaTotal = Math.round(100*this.sumaTotal) / 100;
      });
      this.porcTotal =this.sumaTotal;
      return this.porcTotal;
    } else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  sendEstado(value: boolean) {
    this.estadoFormProdCanToParent.emit(value);
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }

  validacionItemControl(value:string):AbstractControl{
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
    if (this.lstProductoCanales != null) { //Verifica la carga de informacion desde el Parent
      this.lstProductoCanales.forEach(producto => {
        for(let canal of producto.lstObjetos.map(t => t)){
          let control: FormControl = new FormControl(canal.porcentaje, Validators.compose([
            Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
            this.groupForm.addControl(producto.objeto.codigo+canal.codigo,control);
        }  
      });
      return true;
    } else {
      return false;
    }
  }
}
