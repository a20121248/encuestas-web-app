import { Injectable } from "@angular/core";
import { Eps } from "../models/eps";
import { Encuesta } from "src/app/shared/models/encuesta";
import { throwError, of, Observable } from "rxjs";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from "@angular/common/http";
import { AuthService } from "src/app/shared/services/auth.service";
import { Router } from "@angular/router";
import { AppConfig } from "src/app/shared/services/app.config";

@Injectable({
  providedIn: "root"
})
export class LineaService {
  private httpHeaders = new HttpHeaders({ "Content-Type": "application/json" });
  protected urlServer = AppConfig.settings.urlServer;

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}

  private agregarAuthorizationHeader() {
    let token = this.authService.token;
    if (token != null) {
      return this.httpHeaders.append("Authorization", "Bearer " + token);
    }
    return this.httpHeaders;
  }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {
      this.router.navigate(["/login"]);
      return true;
    }
    return false;
  }

  errorHandler(error: any): void {
    console.log(error);
  }

  obtenerEncuesta(posicionCodigo: string): Observable<Encuesta> {
    const str1 = "procesos/" + this.authService.proceso.id;
    const str2 = "colaboradores/" + posicionCodigo;
    const str3 = "encuesta/linea";
    // const url = this.urlServer.api + str1 + "/" + str2 + "/" + str3;
    const url = "https://api.myjson.com/bins/r8drx";

    return this.http.get<Encuesta>(url, {
      headers: this.agregarAuthorizationHeader()
    });
  }

  guardarEncuesta(encuesta: Encuesta, posicionCodigo: string): Observable<any> {
    const str1 = "procesos/" + this.authService.proceso.id;
    const str2 = "colaboradores/" + posicionCodigo;
    const str3 = "encuesta/linea";
    const url = this.urlServer.api + str1 + "/" + str2 + "/" + str3;
    console.log(encuesta);
    return this.http.post<any>(url, encuesta, {
      headers: this.agregarAuthorizationHeader()
    });
  }
}
