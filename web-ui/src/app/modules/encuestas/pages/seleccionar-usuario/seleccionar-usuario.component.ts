import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Usuario } from '../../../../shared/models/usuario';
import { UsuarioService } from '../../../../shared/services/usuario.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-seleccionar-usuario',
  templateUrl: './seleccionar-usuario.component.html',
  styleUrls: ['./seleccionar-usuario.component.css']
})
export class SeleccionarUsuarioComponent implements OnInit {

  dcUsuario = ['codigo', 'nombre', 'area', 'completar'];
  lstUsuario: Usuario[];



  titulo = 'Listado de Usuarios';
  constructor(private usuarioService: UsuarioService,
    private router: Router) { }


  ngOnInit() {
    this.usuarioService.getUsuariosDependientes().subscribe(usuarios => {
      this.lstUsuario = usuarios;
    }
    );
  }

  getUsuariosDependientes() {
    return this.lstUsuario;
  }

  completarEncuesta(usuario: Usuario) {
    //this.authService.setSeleccionado(usuario);
    //this.router.navigateByUrl("/encuestas");
    //swal.fire('titul', 'usuario: ' + this.authService.seleccionado.nombre, 'info');
  }
}
