import { Injectable } from "@angular/core";
import { Usuario } from "src/app/shared/models/usuario";
import { throwError, of, Observable } from "rxjs";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from "@angular/common/http";
import { Router, ActivatedRoute } from "@angular/router";
import { AppConfig } from "src/app/shared/services/app.config";
import { AuthService } from "src/app/shared/services/auth.service";

@Injectable({
  providedIn: "root"
})
export class UsuarioService {
  protected urlServer = AppConfig.settings.urlServer;
  private httpHeaders = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private _activatedRoute: ActivatedRoute
  ) {}

  private agregarAuthorizationHeader() {
    let token = this.authService.token;
    if (token != null) {
      return this.httpHeaders.append("Authorization", "Bearer " + token);
    }
    return this.httpHeaders;
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return throwError(error);
  }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {
      this.router.navigate(["login"]);
      return true;
    }
    return false;
  }

  getUsuario(codigo: string): Observable<Usuario> {
    return this.http.get<Usuario>(this.urlServer.api + "usuarios/" + codigo);
  }

  getUsuarioByPosicionCodigo(codigo: string): Observable<Usuario> {
    return this.http.get<Usuario>(
      this.urlServer.api + "procesos/"+this.authService.proceso.id+"/usuarios/posicion/" + codigo
    );
  }

  getUsuariosDependientes(): Observable<Usuario[]> {
    const str1 = "procesos/" + this.authService.proceso.id;
    const str2 = "usuarios-dependientes/" + this.authService.usuario.posicionCodigo;

    return this.http.get<Usuario[]>(this.urlServer.api + str1 + "/" + str2, {
      headers: this.agregarAuthorizationHeader()
    });
  }
}
