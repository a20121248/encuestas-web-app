import { Injectable } from '@angular/core';
import { Usuario } from 'src/app/shared/models/usuario';
import { map } from 'rxjs/operators';
import { HttpClient, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { AppConfig } from 'src/app/shared/services/app.config';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  protected urlServer = AppConfig.settings.urlServer;
  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {
      this.router.navigate(['login']);
      return true;
    }
    return false;
  }

  count(): Observable<number> {
    const url = `usuarios/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
  }

  upload(formData: FormData): Observable<any> {
    const url = `${this.urlServer.api}centros/cargar`;
    return this.http.post<any>(url, formData, {
      reportProgress: true,
      observe: 'events'
    }).pipe(map((event) => {
      switch (event.type) {
        case HttpEventType.UploadProgress:
          return { porcentaje: event.loaded / event.total };
        case HttpEventType.Response:
          return event.body;
        default:
          return `Unhandled event: ${event.type}`;
      }
    })
    );
  }

  getUsuario(codigo: string): Observable<Usuario> {
    return this.http.get<Usuario>(this.urlServer.api + 'usuarios/' + codigo);
  }

  getUsuarioByPosicionCodigo(codigo: string): Observable<Usuario> {
    return this.http.get<Usuario>(
      `${this.urlServer.api}procesos/${this.authService.proceso.id}/usuarios/posicion/${codigo}`
    );
  }

  getUsuariosDependientes(): Observable<Usuario[]> {
    const url = `procesos/${this.authService.proceso.id}/usuarios-dependientes/${this.authService.usuario.posicion.codigo}`;
    return this.http.get<Usuario[]>(`${this.urlServer.api}${url}`);
  }
}
