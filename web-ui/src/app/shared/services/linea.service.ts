import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { Encuesta } from 'src/app/shared/models/encuesta';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/shared/services/auth.service';
import { AppConfig } from 'src/app/shared/services/app.config';
import { Objeto } from '../models/objeto';

@Injectable({
  providedIn: 'root'
})
export class LineaService {
  protected urlServer = AppConfig.settings.urlServer;

  constructor(public authService: AuthService,
              private http: HttpClient) {
  }

  findAll(): Observable<Objeto[]> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.get<Objeto[]>(url);
  }

  obtenerEncuesta(usuario: Usuario): Observable<Encuesta> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea/${usuario.posicion.perfil.id}`;
    console.log(`${this.urlServer.api}${url2}`);
    return this.http.get<Encuesta>(`${this.urlServer.api}${url2}`);
  }

  guardarEncuesta(encuesta: Encuesta, usuario: Usuario): Observable<any> {
    const url1 = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/`;
    const url2 = `${url1}encuesta/linea`;
    console.log(`${this.urlServer.api}${url2}`);
    console.log('inicio guardar:');
    console.log(encuesta);
    console.log(usuario);
    console.log('fin guardar:');
    return this.http.post<any>(`${this.urlServer.api}${url2}`, encuesta);
  }

  deleteAll(): Observable<any> {
    const url = `${this.urlServer.api}lineas/eliminar-todos`;
    return this.http.post<any>(url, null);
  }

  create(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.post<any>(url, linea);
  }

  edit(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas`;
    return this.http.put<any>(url, linea);
  }

  delete(linea: Objeto): Observable<any> {
    const url = `${this.urlServer.api}lineas/${linea.id}`;
    return this.http.delete<any>(url);
  }
}
