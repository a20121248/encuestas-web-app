import { Injectable } from '@angular/core';
import { LineaCanal } from '../models/linea-canal';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from '../models/usuario';
import { Encuesta } from '../models/encuesta';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { AppConfig } from './app.config';
import { LINEA_CANAL_mock } from './linea-canal.json';

@Injectable({
  providedIn: 'root'
})
export class LineaCanalService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }

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

  obtenerEncuesta(usuario: Usuario): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea-canal/${usuario.posicion.perfil.id}`;
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea-canal`;
    return this.http.post<any>(`${this.urlServer.api}${url2}`, encuesta);
  }

  downloadEncuesta(usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${this.urlServer.api}${url1}encuesta/linea-canal/${usuario.posicion.perfil.id}/descargar`;
    return this.http.post(url2, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }
}
