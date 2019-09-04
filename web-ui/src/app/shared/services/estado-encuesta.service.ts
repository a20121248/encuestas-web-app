import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { Usuario } from 'src/app/shared/models/usuario';
import { AppConfig } from 'src/app/shared/services/app.config';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class EstadoEncuestaService {
  protected urlServer = AppConfig.settings.urlServer;
  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }

  updateEstadoEncuestaLineaCanal(usuario: Usuario, lineaId:number, canalId: number){
    
  }

  updateEstadoEncuestaLinea(usuario: Usuario, lineaId:number){
  }

  updateEstadoEncuestaEmpresa(usuario: Usuario, empresaId: number){

  }

  updateEstadoEncuestaUsuario(usuario: Usuario){

  }
}
