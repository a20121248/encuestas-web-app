import { Injectable } from '@angular/core';
import { AppConfig } from './app.config';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient) { }

  generarReporteControl(filtro: any): Observable<any> {
    const url = `${this.urlServer.api}reportes/control`;
    return this.http.post(url, filtro, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  generarReporteEmpresas(filtro: any): Observable<any> {
    const url = `${this.urlServer.api}reportes/empresas`;
    return this.http.post(url, filtro, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  generarReporteConsolidado(filtro: any): Observable<any> {
    const url = `${this.urlServer.api}reportes/consolidado`;
    return this.http.post(url, filtro, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }
}
