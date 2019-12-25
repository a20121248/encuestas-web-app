import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(public authService: AuthService,
              private http: HttpClient) {
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}productos`;
    return this.http.get<Objeto[]>(url);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}productos/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(producto: Objeto): Observable<any> {
    const url = `${this.urlServer.api}productos`;
    return this.http.post<any>(url, producto);
  }

  edit(producto: Objeto): Observable<any> {
    const url = `${this.urlServer.api}productos`;
    return this.http.put<any>(url, producto);
  }

  delete(producto: Objeto): Observable<any> {
    const url = `${this.urlServer.api}productos/${producto.id}`;
    return this.http.delete<any>(url);
  }

  softDelete(producto: Objeto): Observable<any> {
    const url = `${this.urlServer.api}productos/${producto.id}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(producto: Objeto): Observable<any> {
    const url = `${this.urlServer.api}productos/${producto.id}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
