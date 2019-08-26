import { Injectable } from '@angular/core';
import { throwError, of, Observable } from 'rxjs';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}


  errorHandler(error: any): void {
    console.log(error);
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}productos`;
    return this.http.get<Objeto[]>(url);
  }
}
