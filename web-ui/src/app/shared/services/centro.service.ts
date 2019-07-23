import { Injectable } from '@angular/core';
import { Centro } from '../models/centro';
import { throwError, of, Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class CentroService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas/1/centros';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  listCentros: Centro[] = [];

  getCentro(): Observable<Centro[]> {

    return this.http.get<Centro[]>(this.urlEndPoint);
    
  }
  getEpsPorc(): Observable<Centro[]> {

    return this.http.get<Centro[]>(this.urlEndPoint);
  }

  postRespuesta(centros: Centro[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(centros)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
