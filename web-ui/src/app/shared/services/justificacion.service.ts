import { Injectable } from '@angular/core';
import { Justificacion } from '../models/justificacion';
import { throwError, of, Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class JustificacionService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/justificaciones';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  getJustificaciones(): Observable<Justificacion[]> {

    return this.http.get<Justificacion[]>(this.urlEndPoint);  
  }

  postRespuesta(justificaciones: Justificacion[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(justificaciones)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
