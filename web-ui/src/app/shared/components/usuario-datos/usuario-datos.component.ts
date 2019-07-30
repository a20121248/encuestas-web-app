import { AuthService } from 'src/app/shared/services/auth.service';
import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';

@Component({
  selector: 'app-usuario-datos',
  templateUrl: './usuario-datos.component.html',
  styleUrls: ['./usuario-datos.component.css']
})
export class UsuarioDatosComponent implements OnInit {
  @Input() posicionCodigo: string;
  usuario: Usuario;

  constructor(
    public authService: AuthService,
    private usuarioService: UsuarioService,
    private http: HttpClient) {
    this.usuario = new Usuario();
  }

  ngOnInit() {
    this.usuarioService.getUsuarioByPosicionCodigo(this.posicionCodigo).subscribe(usuario => {
      if (usuario) {
        this.usuario = usuario;
      }
    });
  }
}


