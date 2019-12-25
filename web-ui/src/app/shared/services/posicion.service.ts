import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { AppConfig } from './app.config';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Proceso } from '../models/Proceso';
import { Posicion } from '../models/posicion';

@Injectable({
  providedIn: 'root'
})
export class PosicionService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) { }

  count(): Observable<number> {
    const url = `posiciones/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
  }

  countDatos(procesoId: number): Observable<number> {
    const url = `${this.urlServer.api}procesos/${procesoId}/cantidad-datos-posiciones`;
    return this.http.get<number>(url);
  }

  findByProcesoIdAndUsuarioCodigo(procesoId: number, usuarioCodigo: string): Observable<Posicion> {
    const url = `${this.urlServer.api}procesos/${procesoId}/usuarios/${usuarioCodigo}/posicion`;
    return this.http.get<Posicion>(url);
  }

  upload(formData: FormData): Observable<any> {
    const url = `${this.urlServer.api}posiciones/cargar`;
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
    const url = `${this.urlServer.api}posiciones/descargar`;
    return this.http.post(url, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  uploadDatos(procesoId: number, formData: FormData): Observable<any> {
    const url = `${this.urlServer.api}procesos/${procesoId}/cargar-datos-posiciones`;
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

  downloadDatos(procesoId: number): Observable<any> {
    const url = `${this.urlServer.api}procesos/${procesoId}/descargar-datos-posiciones`;
    return this.http.post(url, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  deleteDatos(proceso: Proceso): Observable<any> {
    const url = `${this.urlServer.api}posiciones/eliminar-datos`;
    return this.http.post<any>(url, proceso);
  }

  findAll(): Observable<Posicion[]> {
    const url = `${this.urlServer.api}posiciones`;
    return this.http.get<Posicion[]>(url);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}posiciones/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(posicion: Posicion): Observable<any> {
    const url = `${this.urlServer.api}posiciones`;
    return this.http.post<any>(url, posicion);
  }

  edit(posicion: Posicion): Observable<any> {
    const url = `${this.urlServer.api}posiciones`;
    return this.http.put<any>(url, posicion);
  }

  delete(posicion: Posicion): Observable<any> {
    const url = `${this.urlServer.api}posiciones/${posicion.codigo}`;
    return this.http.delete<any>(url);
  }

  softDelete(posicion: Posicion): Observable<any> {
    const url = `${this.urlServer.api}posiciones/${posicion.codigo}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(posicion: Posicion): Observable<any> {
    const url = `${this.urlServer.api}posiciones/${posicion.codigo}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
