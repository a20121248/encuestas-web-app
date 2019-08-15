import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfig } from './app.config';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(private http: HttpClient) {
  }

  downloadFile(): Observable<any> {
    const url = `${this.urlServer.api}procesos/2/reportes/control`;

    /*return this.http.get(`${this.urlServer.api}convertFlatFileToExcel.do`,{
      headers : {
          'Content-Type' : undefined,
          'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      }});*/

    return this.http.get(url, {
      responseType: 'blob'
    });
/*
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'text/csv; charset=utf-8');

    return this.http.get('/api/procesos/2/reportes/control/' + fileName, {
      headers: headers,
      observe: 'response',
      responseType: 'text'
    });
*/
  }

}
