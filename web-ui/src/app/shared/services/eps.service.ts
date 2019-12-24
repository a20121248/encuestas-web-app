import { Injectable } from '@angular/core';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class EpsService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}

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
    const url = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/encuesta/eps`;
    return this.http.get<Encuesta>(this.urlServer.api + url);
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/encuesta/eps`;
    return this.http.post<any>(this.urlServer.api + url, encuesta);
  }

  downloadEncuesta(usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${this.urlServer.api}${url1}encuesta/eps/descargar`;
    return this.http.post(url2, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }
}
