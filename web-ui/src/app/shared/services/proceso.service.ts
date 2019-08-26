import { Injectable } from '@angular/core';
import { AppConfig } from './app.config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Proceso } from '../models/proceso';

@Injectable({
  providedIn: 'root'
})
export class ProcesoService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(private http: HttpClient) { }

  findById(procesoId: number): Observable<Proceso> {
    const url = `${this.urlServer.api}procesos/${procesoId}`;
    return this.http.get<Proceso>(url);
  }

  findAll(): Observable<Proceso[]> {
    const url = `${this.urlServer.api}procesos`;
    return this.http.get<Proceso[]>(url);
  }

  crear(proceso: Proceso): Observable<any> {
    const url = `${this.urlServer.api}procesos`;
    console.log(url);
    return this.http.post<any>(url, proceso);
  }

  editar(proceso: Proceso): Observable<any> {
    const url = `${this.urlServer.api}procesos`;
    console.log(url);
    return this.http.put<any>(url, proceso);
  }

  delete(proceso: Proceso): Observable<any> {
    const url = `${this.urlServer.api}procesos/${proceso.id}`;
    console.log(url);
    return this.http.delete<any>(url);
  }
}
