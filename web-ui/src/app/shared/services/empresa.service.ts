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
  //private url = 'api';
  //private urlJsonServer = 'http://' + AppConfig.settings.apiServer.ruta_host + ':' + AppConfig.settings.apiServer.puerto_host
//    + '/SimuladorIFRS9/rest/parametrizacion';
  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  listEmpresas: Empresa[] = [];

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
