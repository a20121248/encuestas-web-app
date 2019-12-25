import { Injectable } from '@angular/core';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class CanalService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(public authService: AuthService,
              private http: HttpClient) {
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}canales`;
    return this.http.get<Objeto[]>(url);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}canales/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(canal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}canales`;
    return this.http.post<any>(url, canal);
  }

  edit(canal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}canales`;
    return this.http.put<any>(url, canal);
  }

  delete(canal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}canales/${canal.id}`;
    return this.http.delete<any>(url);
  }

  softDelete(canal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}canales/${canal.id}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(canal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}canales/${canal.id}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
