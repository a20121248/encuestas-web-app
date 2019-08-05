import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/shared/models/usuario';
import { Tipo } from 'src/app/shared/models/tipo';
import { AppConfig } from 'src/app/shared/services/app.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _usuario: Usuario;
  private _proceso: Tipo;
  private _token: string;
  protected urlServer = AppConfig.settings.urlServer;

  constructor(private http: HttpClient) { }

  public get usuario(): Usuario {
    if (this._usuario != null) {
      return this._usuario;
    } else if (this._usuario == null && localStorage.getItem('usuario') != null) {
      this._usuario = JSON.parse(localStorage.getItem('usuario')) as Usuario;
      return this._usuario;
    }
    return new Usuario();
  }

  public get proceso(): Tipo {
    if (this._proceso != null) {
      return this._proceso;
    } else if (this._proceso == null && localStorage.getItem('proceso') != null) {
      this._proceso = JSON.parse(localStorage.getItem('proceso')) as Tipo;
      return this._proceso;
    }
    return new Tipo();
  }

  setProceso(proceso: Tipo): void {
    this._proceso = proceso;
  }

  public get token(): string {
    if (this._token != null) {
      return this._token;
    } else if (this._token == null && localStorage.getItem('token') != null) {
      this._token = localStorage.getItem('token');
      return this._token;
    }
    return null;
  }

  login(usuario: Usuario): Observable<any> {
    const urlEndpoint = this.urlServer.oauth + 'token';
    console.log(urlEndpoint);
    const credenciales = btoa('angularapp' + ':' + '12345');
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + credenciales
    });
    let params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', usuario.codigo);
    params.set('password', usuario.contrasenha);
    return this.http.post<any>(urlEndpoint, params.toString(), { headers: httpHeaders });
  }

  guardarUsuario(accessToken: string): void {
    const payload = this.obtenerDatosToken(accessToken);
    this._usuario = new Usuario();
    this._usuario.codigo = payload.user_name;
    this._usuario.nombre = payload.nombre;
    this._proceso = payload.proceso;
    this._usuario.posicion = payload.posicion;

    localStorage.setItem('usuario', JSON.stringify(this._usuario));
    localStorage.setItem('proceso', JSON.stringify(this._proceso));
  }

  guardarToken(accessToken: string): void {
    this._token = accessToken;
    localStorage.setItem('token', accessToken);
  }

  obtenerDatosToken(accessToken: string): any {
    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split('.')[1]));

    }
  }

  isAuthenticated(): boolean {
    let payload = this.obtenerDatosToken(this.token);
    if (payload != null && payload.user_name && payload.user_name.length > 0) {
      return true;
    }
    return false;
  }

  logout(): void {
    this._token = null;
    this._usuario = null;
    //localStorage.clear();
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    localStorage.removeItem('proceso');
  }
}
