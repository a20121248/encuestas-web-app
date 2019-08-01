import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

import { AppConfig } from 'src/app/shared/services/app.config';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { MATRIZ } from 'src/app/shared/services/producto-subcanal.json'
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

  private agregarAuthorizationHeader() {
    let token = this.authService.token;
    if (token != null) {
      return this.httpHeaders.append('Authorization', 'Bearer ' + token);
    }
    return this.httpHeaders;
  }

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

  obtenerMatriz():Observable<any>{
    return of(MATRIZ);
  }
}