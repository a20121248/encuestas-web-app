import { Injectable } from "@angular/core";
import { Usuario } from '../models/usuario';
import { Encuesta } from "src/app/shared/models/encuesta";
import { throwError, of, Observable } from "rxjs";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from "@angular/common/http";
import { AuthService } from "src/app/shared/services/auth.service";
import { Router } from "@angular/router";
import { AppConfig } from "src/app/shared/services/app.config";

@Injectable({
  providedIn: "root"
})
export class LineaService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}


  errorHandler(error: any): void {
    console.log(error);
  }

  obtenerEncuesta(usuario: Usuario): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea/${usuario.posicion.perfil.id}`;
    console.log(`${this.urlServer.api}${url2}`);
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }


  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/encuesta/centro`;
    return this.http.post<any>(this.urlServer.api + url, encuesta);
  }

}
