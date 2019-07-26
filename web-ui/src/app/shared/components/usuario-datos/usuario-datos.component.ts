import { Component, OnInit, Input } from '@angular/core';
import { Usuario } from '../../../shared/models/usuario';
import { AuthService } from 'src/app/shared/services/auth.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { MatTableDataSource } from '@angular/material';
import { UsuarioService } from '../../services/usuario.service';


@Component({
  selector: 'app-usuario-datos',
  templateUrl: './usuario-datos.component.html',
  styleUrls: ['./usuario-datos.component.css']
})
export class UsuarioDatosComponent implements OnInit {

  @Input() codigo: string;
  usuario: Usuario;

  constructor(private usuarioService: UsuarioService, private http: HttpClient, public authService: AuthService) {
      this.usuario = new Usuario();
    }

  ngOnInit() {
    this.usuarioService.getUsuario(this.codigo).subscribe(usuario => { this.usuario = usuario});
    console.log(this.codigo);
  }

}


