import { Injectable } from '@angular/core';
import { Justificacion } from '../models/justificacion';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { AppConfig } from './app.config';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})

export class JustificacionService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
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

  getJustificaciones(): Observable<Justificacion[]> {
    const url = 'justificaciones';
    return this.http.get<Justificacion[]>(this.urlServer.api + url);
  }
}
