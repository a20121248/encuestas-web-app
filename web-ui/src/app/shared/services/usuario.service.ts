import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { throwError, of, Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private url:string = 'http://hp840g-malfbl35:8080/api/usuario-datos/589980';

  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  getUsuario(): Observable<Usuario> {

  return this.http.get<Usuario>(this.url);
    
  }
}
