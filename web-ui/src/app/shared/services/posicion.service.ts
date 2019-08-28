import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { AppConfig } from './app.config';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Proceso } from '../models/Proceso';

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

  uploadDatos(proceso: Proceso, formData: FormData): Observable<any> {
    const url = `${this.urlServer.api}procesos/${proceso.id}/cargar-datos-posiciones`;
    console.log(this.uploadDatos);
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
}
