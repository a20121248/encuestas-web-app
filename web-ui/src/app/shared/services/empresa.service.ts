import { Injectable } from '@angular/core';
import { Empresa } from '../models/empresa';
import { throwError, of, Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';

import { AppConfig } from './config/app.config';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }

  getEmpresas(): Observable<Empresa[]> {

    return this.http.get<Empresa[]>(this.urlEndPoint);
    
  }
  getEmpresaPorc(): Observable<Empresa[]> {

    return this.http.get<Empresa[]>(this.urlEndPoint);
  }

  postRespuesta(empresas: Empresa[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(empresas)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
