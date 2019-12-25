import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class SubcanalService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(public authService: AuthService,
              private http: HttpClient) {
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}subcanales`;
    return this.http.get<Objeto[]>(url);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}subcanales/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(subcanal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}subcanales`;
    return this.http.post<any>(url, subcanal);
  }

  edit(subcanal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}subcanales`;
    return this.http.put<any>(url, subcanal);
  }

  delete(subcanal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}subcanales/${subcanal.id}`;
    return this.http.delete<any>(url);
  }

  softDelete(subcanal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}subcanales/${subcanal.id}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(subcanal: Objeto): Observable<any> {
    const url = `${this.urlServer.api}subcanales/${subcanal.id}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
