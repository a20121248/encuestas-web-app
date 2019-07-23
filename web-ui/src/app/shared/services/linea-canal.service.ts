import { Injectable } from '@angular/core';
import { LineaCanal } from '../models/linea-canal';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LineaCanalService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }

  getLineaCanal(): Observable<LineaCanal[]> {

    return this.http.get<LineaCanal[]>(this.urlEndPoint);
    
  }
  getLineaCanalPorc(): Observable<LineaCanal[]> {
    return this.http.get<LineaCanal[]>(this.urlEndPoint);
  }

  postRespuesta(lstLineaCanal: LineaCanal[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(lstLineaCanal)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
