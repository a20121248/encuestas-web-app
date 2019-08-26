import { Injectable } from '@angular/core';
import { throwError, of, Observable } from 'rxjs';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { AppConfig } from 'src/app/shared/services/app.config';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Centro } from 'src/app/shared/models/centro';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class CentroService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}

  count(): Observable<number> {
    const url = `centros/cantidad`;
    return this.http.get<number>(`${this.urlServer.api}${url}`);
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

  findById(centroId: number): Observable<Centro> {
    const url = `${this.urlServer.api}centros/${centroId}`;
    return this.http.get<Centro>(url);
  }

  findAll(): Observable<Centro[]> {
    const url = `${this.urlServer.api}centros`;
    return this.http.get<Centro[]>(url);
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

  obtenerEncuesta(usuario: Usuario): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/centro/${usuario.posicion.centro.nivel}/${usuario.posicion.perfil.id}`;
    console.log(`${this.urlServer.api}${url2}`);
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
    // return this.http.get<Encuesta>('https://api.myjson.com/bins/7oi9p');
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/encuesta/centro`;
    return this.http.post<any>(this.urlServer.api + url, encuesta);
  }
}
