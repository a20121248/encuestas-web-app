import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/shared/models/usuario';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  titulo = 'Iniciar sesión';
  usuario: Usuario;

  constructor() {
    this.usuario = new Usuario();
  }

  ngOnInit() {
  }

  login(): void {
    console.log(this.usuario);
    if (this.usuario.codigo == null || this.usuario.contrasenha == null) {
      swal.fire('Error en iniciar sesión', 'Matrícula o contraseña vacías.', 'error')
      return;
    }
  }

}
