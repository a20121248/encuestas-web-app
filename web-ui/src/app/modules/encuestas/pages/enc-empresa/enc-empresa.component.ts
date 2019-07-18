import { Component, OnInit } from '@angular/core';
import { Empresa } from  '../../../../shared/models/empresa';
import { EmpresaComponent } from '../../components/empresa/empresa.component';
import { AfterViewInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-enc-empresa',
  templateUrl: './enc-empresa.component.html',
  styleUrls: ['./enc-empresa.component.css']
})
export class EncEmpresaComponent implements OnInit {
  lstEmpresas: Empresa[] ;
  

  constructor() { }
  
  @ViewChild(EmpresaComponent, {static: false})  empresaComponent: EmpresaComponent ;

  ngOnInit() {
  }

  guardarEncuesta(){
  this.lstEmpresas = this.empresaComponent.lstEmpresas;
  console.log(this.lstEmpresas);

  }
  


}
