import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Usuario } from '../../../../shared/models/usuario';
import { UsuarioService } from '../../../../shared/services/usuario.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';
import swal from 'sweetalert2';
import { UsuarioSeleccionadoService } from 'src/app/shared/services/usuario-seleccionado.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-seleccionar-usuario',
  templateUrl: './seleccionar-usuario.component.html',
  styleUrls: ['./seleccionar-usuario.component.css']
})
export class SeleccionarUsuarioComponent implements OnInit {

  dcUsuario = ['codigo', 'nombre', 'posicion', 'area', 'completar'];
  lstUsuario: Usuario[];

  titulo = 'Colaboradores';
  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private usuarioSeleccionadoService: UsuarioSeleccionadoService,
    private titleService: Title
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Colaboradores');
    this.usuarioService.getUsuariosDependientes().subscribe(usuarios => {
      this.lstUsuario = usuarios;
    });
  }
}
