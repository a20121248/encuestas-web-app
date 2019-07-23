import { Component, OnInit } from '@angular/core';
import { Empresa } from  '../../../../shared/models/empresa';
import { EmpresaComponent } from '../../components/empresa/empresa.component';
import { ViewChild } from '@angular/core';
import { EmpresaService } from 'src/app/shared/services/empresa.service';



@Component({
  selector: 'app-enc-empresa',
  templateUrl: './enc-empresa.component.html',
  styleUrls: ['./enc-empresa.component.css']
})
export class EncEmpresaComponent implements OnInit {
  lstEmpresas: Empresa[] ;
  

  constructor(private empresaService:EmpresaService) { }
  
  @ViewChild(EmpresaComponent, {static: false})  empresaComponent: EmpresaComponent ;

  ngOnInit() {
  }

  guardarEncuesta(){
    this.lstEmpresas = this.empresaComponent.getLstEmpresas();
    this.empresaService.postRespuesta (this.lstEmpresas);
  }
}
