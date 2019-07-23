import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private url = 'http://hp840g-malfbl35:8080/api/usuario-datos/589980';

  constructor(private http: HttpClient, private router: Router) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {
      this.router.navigate(['login']);
      return true;
    }
    return false;
  }

  getUsuario(): Observable<Usuario> {
    return this.http.get<Usuario>(this.url);
  }
}
