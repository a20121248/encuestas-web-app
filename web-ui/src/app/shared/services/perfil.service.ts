import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { AppConfig } from './app.config';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) { }

  count(): Observable<number> {
    const url = `perfiles/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
  }
}
