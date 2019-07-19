import { Component, OnInit } from '@angular/core';
import {EmpresaService} from '../../../../shared/services/empresa.service';
import { Empresa } from  '../../../../shared/models/empresa';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { MatTableDataSource } from '@angular/material';

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
  

  dcEmpresa = ['nombre', 'porcentaje'];
  dataSource: MatTableDataSource<Empresa>;

  // empresa: Empresa[] = [
  //   { id: 1, codigo: '001', nombre: 'PRIMA' , fechaCreacion: new Date (), porcentaje: 1},
  //   { id: 2, codigo: '002', nombre: 'PACIFICO' , fechaCreacion: new Date (), porcentaje: 2},
  //   { id: 3, codigo: '003', nombre: 'BCP' , fechaCreacion: new Date (), porcentaje: 3},
  //   { id: 4, codigo: '004', nombre: 'CREDICORP CAPITAL' , fechaCreacion: new Date (), porcentaje: 4},
  // ];
 
  constructor(private empresaService: EmpresaService, 
              private http: HttpClient) { 
                this.dataSource = new MatTableDataSource<Empresa>()
                console.log(this.lstEmpresas);
                this.dataSource.data = this.lstEmpresas;
              }

  ngOnInit() {
    this.empresaService.getEmpresas().subscribe(empresas=>{   this.dataSource.data=empresas; this.lstEmpresas=empresas});
    console.log(this.lstEmpresas);
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
