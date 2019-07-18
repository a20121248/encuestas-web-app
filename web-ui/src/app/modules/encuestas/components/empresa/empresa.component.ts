import { Component, OnInit } from '@angular/core';
import {EmpresaService} from '../../../../shared/services/empresa.service';
import { Empresa } from  '../../../../shared/models/empresa';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})
export class EmpresaComponent implements OnInit {
  lstEmpresas: Empresa[] ;

  constructor(private empresaService: EmpresaService) { }
  
  ngOnInit() {
    this.empresaService.getEmpresas().subscribe(empresas=> this.lstEmpresas=empresas);
  }

  editField: number;

  updateList(id: number, property: string, event: any) {
    const editField = parseFloat(event.target.textContent);
    this.lstEmpresas[id][property] = editField;
  }

  changeValue(id: number, property: string, event: any) {
    this.editField = parseFloat(event.target.textContent);
  }
  
  getLstEmpresas(){
    return this.lstEmpresas;

  }
}
