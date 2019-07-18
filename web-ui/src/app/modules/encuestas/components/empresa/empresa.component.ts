import { Component, OnInit } from '@angular/core';
import {EmpresaService} from '../../../../shared/services/empresa.service';
import { Empresa } from  '../../../../shared/models/empresa';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})


export class EmpresaComponent implements OnInit {
  lstEmpresas: Empresa[] ;
  private url: string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas';
  

 
  constructor(private empresaService: EmpresaService, 
              private http: HttpClient) { }
  
    

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
  postRespuesta(_body: Empresa): any {
    console.log( _body);
    return this.http.post(this.url, _body, httpOptions) ;
  }
}
