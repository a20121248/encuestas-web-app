import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';
import { AppConfig } from './app.config';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PosicionService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) { }

  count(): Observable<number> {
    const url = `posiciones/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
  }
}
