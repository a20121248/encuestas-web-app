import { Injectable } from '@angular/core';
import { Eps } from '../models/eps';
import { throwError, of, Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class EpsService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas/2/centros';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  listEps: Eps[] = [];

  getEps(): Observable<Eps[]> {

    return this.http.get<Eps[]>(this.urlEndPoint);
    
  }
  getEpsPorc(): Observable<Eps[]> {

    return this.http.get<Eps[]>(this.urlEndPoint);
  }

  postRespuesta(eps: Eps[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(eps)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
