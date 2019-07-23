import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Linea } from '../models/linea';

@Injectable({
  providedIn: 'root'
})
export class LineaService {

  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }

  getLinea(): Observable<Linea[]> {
    return this.http.get<Linea[]>(this.urlEndPoint);  
  }

  postRespuesta(lstLinea: Linea[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(lstLinea)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
