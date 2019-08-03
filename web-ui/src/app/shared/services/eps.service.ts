import { Injectable } from '@angular/core';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
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
    const str1 = 'procesos/' + this.authService.proceso.id + '/';
    const str2 = 'colaboradores/' + usuario.posicion.codigo + '/';
    const str3 = 'encuesta/eps';
    const url = this.urlServer.api + str1 + str2 + str3;
    return this.http.get<Encuesta>(url);
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const str1 = 'procesos/' + this.authService.proceso.id + '/';
    const str2 = 'colaboradores/' + usuario.posicion.codigo + '/';
    const str3 = 'encuesta/eps';
    const url = this.urlServer.api + str1 + str2 + str3;
    console.log(encuesta);
    return this.http.post<any>(url, encuesta);
  }
}
