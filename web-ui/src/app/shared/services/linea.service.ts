import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class LineaService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(public authService: AuthService,
              private http: HttpClient) {
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.get<Objeto[]>(url);
  }

  obtenerEncuesta(usuario: Usuario): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea/${usuario.posicion.perfil.id}`;
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea`;
    return this.http.post<any>(`${this.urlServer.api}${url2}`, encuesta);
  }

  downloadEncuesta(usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${this.urlServer.api}${url1}encuesta/linea/${usuario.posicion.perfil.id}/descargar`;
    return this.http.post(url2, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}lineas/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.post<any>(url, linea);
  }

  edit(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.put<any>(url, linea);
  }

  delete(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas/${linea.id}`;
    return this.http.delete<any>(url);
  }

  softDelete(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas/${linea.id}/soft-delete`;
    return this.http.put<any>(url, null);
  }

  softUndelete(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas/${linea.id}/soft-undelete`;
    return this.http.put<any>(url, null);
  }
}
