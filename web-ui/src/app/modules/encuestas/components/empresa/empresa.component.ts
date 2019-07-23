import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {EmpresaService} from '../../../../shared/services/empresa.service';
import { Empresa } from  '../../../../shared/models/empresa';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})


export class EmpresaComponent implements OnInit {
  private lstEmpresas: Empresa[] = [] ;
  dcEmpresa = ['nombre', 'porcentaje','ingresar'];

  constructor(
    private empresaService: EmpresaService,
    private router: Router) { }

  ngOnInit() {
    this.empresaService.getEmpresas().subscribe(empresas=>{ 
      this.lstEmpresas=empresas;
      }
    );
  }
  
  getLstEmpresas(){
    return this.lstEmpresas;
  }

  getTotalPorcentaje(){
    return this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }

  revisarEmpresa(codigo: string):boolean{
    if(codigo =="1" || codigo =="2") return true;
    else return false;
  }

  irMasDetalle(empresa: Empresa){
    if(empresa.nombre.toUpperCase().includes("EPS")){
      this.router.navigateByUrl("/encuestas/eps");
    }
    if(empresa.nombre.toUpperCase().includes("PGA")){
      this.router.navigateByUrl("/encuestas/centros");
    }
  }
}
