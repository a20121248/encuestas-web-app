import { Injectable } from '@angular/core';
import { Area } from '../models/area';
import { AppConfig } from './app.config';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AreaService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(private http: HttpClient) { }

  findById(areaId: number): Observable<Area> {
    const url = `${this.urlServer.api}areas/${areaId}`;
    return this.http.get<Area>(url);
  }

  findAll(): Observable<Area[]> {
    const url = `${this.urlServer.api}areas`;
    console.log(url);
    return this.http.get<Area[]>(url);
  }
}
