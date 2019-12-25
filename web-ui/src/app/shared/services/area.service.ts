import { Injectable } from '@angular/core';
import { Area } from '../models/area';
import { AppConfig } from './app.config';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AreaService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(private http: HttpClient) { }

  count(): Observable<number> {
    const url = `areas/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
  }

  upload(formData: FormData): Observable<any> {
    const url = `${this.urlServer.api}areas/cargar`;
    return this.http.post<any>(url, formData, {
      reportProgress: true,
      observe: 'events'
    }).pipe(map((event) => {
      switch (event.type) {
        case HttpEventType.UploadProgress:
          return { porcentaje: event.loaded / event.total };
        case HttpEventType.Response:
          return event.body;
        default:
          return `Unhandled event: ${event.type}`;
      }
    })
    );
  }

  download(): Observable<any> {
    const url = `${this.urlServer.api}areas/descargar`;
    return this.http.post(url, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  findById(areaId: number): Observable<Area> {
    const url = `${this.urlServer.api}areas/${areaId}`;
    return this.http.get<Area>(url);
  }

  findAll(): Observable<Area[]> {
    const url = `${this.urlServer.api}areas`;
    return this.http.get<Area[]>(url);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}areas/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(area: Area): Observable<any> {
    const url = `${this.urlServer.api}areas`;
    return this.http.post<any>(url, area);
  }

  edit(area: Area): Observable<any> {
    const url = `${this.urlServer.api}areas`;
    return this.http.put<any>(url, area);
  }

  delete(area: Area): Observable<any> {
    const url = `${this.urlServer.api}centros/${area.id}`;
    return this.http.delete<any>(url);
  }

  softDelete(area: Area): Observable<any> {
    const url = `${this.urlServer.api}areas/${area.id}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(area: Area): Observable<any> {
    const url = `${this.urlServer.api}areas/${area.id}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
