import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/shared/models/usuario';
import swal from 'sweetalert2';
import { AuthService } from '../../shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  titulo = 'Iniciar sesión';
  usuario: Usuario;

  constructor(private authService: AuthService, private router: Router) {
    this.usuario = new Usuario();
  }

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      swal.fire('Login', `Hola ${this.authService.usuario.nombre}, ya iniciaste sesión anteriormente.`, 'info');
      this.router.navigate(['/encuestas']);
    }
  }

  login(): void {
    console.log(this.usuario);
    if (this.usuario.codigo == null || this.usuario.contrasenha == null) {
      swal.fire('Error en iniciar sesión', 'Matrícula o contraseña vacías.', 'error');
      return;
    }
    this.authService.login(this.usuario).subscribe(
      response => {
        console.log(response);
        //let payload = JSON.parse(atob(response.access_token.split('.')[1]));
        //console.log(payload);

        this.authService.guardarUsuario(response.access_token);
        this.authService.guardarToken(response.access_token);
        let usuario = this.authService.usuario;
        this.router.navigate(['/encuestas']);
        swal.fire('Login', `Hola ${usuario.nombre}, has iniciado sesión con éxito!`, 'success');
      }, err => {
        if (err.status == 400) {
          swal.fire('Error en iniciar sesión', 'Usuario o clave incorrecta.', 'error');
        }
      }
      );
  }

}
