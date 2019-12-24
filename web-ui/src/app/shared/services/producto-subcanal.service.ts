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
import { ObjetoObjetos } from '../models/objeto-objetos';
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

  obtenerEncuesta(posicionCodigo: string, lineaId: string, canalId: string): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${posicionCodigo}/`;
    const url2 = `${url1}encuesta/producto-subcanal/${lineaId}/${canalId}`;
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }

  guardarEncuesta(encuesta: Encuesta, posicionCodigo: string, lineaId: string, canalId: string): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${posicionCodigo}/`;
    const url2 = `${url1}encuesta/producto-subcanal/${lineaId}/${canalId}`;
    return this.http.post<any>(`${this.urlServer.api}${url2}`, encuesta);
  }

  downloadEncuesta(usuario: Usuario, lineaId: string, canalId: string): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${this.urlServer.api}${url1}encuesta/producto-subcanal/${lineaId}/${canalId}/descargar`;
    return this.http.post(url2, null, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    });
  }
}
