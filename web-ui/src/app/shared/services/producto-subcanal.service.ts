import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

import { AppConfig } from 'src/app/shared/services/app.config';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { MATRIZ } from 'src/app/shared/services/producto-subcanal.json'
import { Usuario } from '../models/usuario';
import { ProductoSubcanal } from '../models/producto-subcanal';
@Injectable({
  providedIn: 'root'
})
export class ProductoSubcanalService {
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

  obtenerEncuesta(usuario: Usuario, lineaId: string, canalId: string): Observable<Encuesta> {
    // const str1 = 'procesos/' + this.authService.proceso.id;
    // const str2 = 'colaboradores/' + usuario.posicion.codigo;
    // const str3 = 'encuesta/linea/producto-subcanal';
    // const url = this.urlServer.api + str1 + '/' + str2 + '/' + str3;
    // return this.http.get<Encuesta>(url, { headers: this.agregarAuthorizationHeader() });
    //return of(MATRIZ);
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/producto-subcanal/${lineaId}/${canalId}`;
    console.log(`${this.urlServer.api}${url2}`);
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }

  guardarEncuesta(matriz: ProductoSubcanal[], posicionCodigo: string): Observable<any> {
    const str1 = 'procesos/' + this.authService.proceso.id + '/';
    const str2 = 'colaboradores/' + posicionCodigo + '/';
    const str3 = 'encuesta/linea/producto-subcanal';
    const url = this.urlServer.api + str1 + str2 + str3;
    return this.http.post<any>(url, matriz);
  }
}
