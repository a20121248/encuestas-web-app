import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Usuario } from 'src/app/shared/models/usuario';
import { ProductoCanal } from 'src/app/shared/models/producto-canal';
import { MATRIZ } from './producto-canal.json';
import { AppConfig } from './app.config';
import { AuthService } from './auth.service';


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

  obtenerEncuesta(usuario: Usuario): Observable<ProductoCanal[]> {
    // const str1 = 'procesos/' + this.authService.proceso.id;
    // const str2 = 'colaboradores/' + usuario.posicion.codigo;
    // const str3 = 'encuesta/linea/producto-subcanal';
    // const url = this.urlServer.api + str1 + '/' + str2 + '/' + str3;
    // return this.http.get<Encuesta>(url, { headers: this.agregarAuthorizationHeader() });
    return of(MATRIZ);
  }

  guardarEncuesta(matriz: ProductoCanal[], posicionCodigo: string): Observable<any> {
    const str1 = 'procesos/' + this.authService.proceso.id + '/';
    const str2 = 'colaboradores/' + posicionCodigo + '/';
    const str3 = 'encuesta/linea/producto-subcanal';
    const url = this.urlServer.api + str1 + str2 + str3;
    return this.http.post<any>(url, matriz);
  }
}
