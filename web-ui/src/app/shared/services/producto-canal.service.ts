import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Usuario } from 'src/app/shared/models/usuario';
import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { MATRIZ } from './producto-canal.json';
import { AppConfig } from './app.config';
import { AuthService } from './auth.service';
import { Encuesta } from '../models/encuesta.js';


@Injectable({
  providedIn: 'root'
})
export class ProductoCanalService {

  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router) { }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {
      this.router.navigate(['/login']);
      return true;
    }
    return false;
  }

  errorHandler(error: any): void {
    console.log(error);
  }

  obtenerEncuesta(posicionCodigo: string, perfilId: number, lineaId: string): Observable<Encuesta> {
    const url1 = `${this.urlServer.api}procesos/${this.authService.proceso.id}/colaboradores/${posicionCodigo}/`;
    const url2 = `${url1}encuesta/producto-canal/${perfilId}/${lineaId}`;
    return this.http.get<Encuesta>(url2);
  }

  guardarEncuesta(encuesta: Encuesta, posicionCodigo: string, lineaId: string): Observable<any> {
    const url1 = `${this.urlServer.api}procesos/${this.authService.proceso.id}/colaboradores/${posicionCodigo}/`;
    const url2 = `${url1}encuesta/producto-canal/${lineaId}`;
    return this.http.post<any>(url2, encuesta);
  }

  downloadEncuesta(usuario: Usuario, lineaId: string): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${this.urlServer.api}${url1}encuesta/producto-canal/${usuario.posicion.perfil.id}/${lineaId}/descargar`;
    return this.http.post(url2, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }
}
