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
        this.router.navigate(['/encuestas']);
        swal.fire('Login', `Hola ${response.codigo}, has iniciado sesión con éxito!`, 'success');
      });
  }

}
