import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Canal } from '../models/canal';

@Injectable({
  providedIn: 'root'
})
export class CanalService {
  private urlEndPoint:string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas/2/centros';
  private httpHeaders =  new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient) { }

  getCanal(): Observable<Canal[]> {
    return this.http.get<Canal[]>(this.urlEndPoint);  
  }

  postRespuesta(lstCanal: Canal[]):any {
    fetch(this.urlEndPoint,
      {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(lstCanal)
      })
      .then(function(res){ console.log(res) })
      .catch(function(res){ console.log(res) });
  }
}
