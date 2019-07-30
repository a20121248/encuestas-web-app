import { AuthService } from 'src/app/shared/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
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
  hide = true;
  selectFormControl = new FormControl('', Validators.required);

  constructor(
    public authService: AuthService,
    private router: Router,
    private titleService: Title) {
    this.usuario = new Usuario();
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Login');
    /*if (this.authService.isAuthenticated()) {
      swal.fire('Login', `Hola ${this.authService.usuario.nombre}, ya iniciaste sesión anteriormente.`, 'info');
      this.router.navigate(['/colaboradores']);
    }*/
  }

  login(): void {
    if (this.usuario.codigo == null || this.usuario.contrasenha == null) {
      swal.fire('Error en iniciar sesión', 'Matrícula o contraseña vacías.', 'error');
      return;
    }
    this.authService.login(this.usuario).subscribe(
      response => {
        //console.log(response);
        //let payload = JSON.parse(atob(response.access_token.split('.')[1]));
        //console.log(payload);

        this.authService.guardarUsuario(response.access_token);
        this.authService.guardarToken(response.access_token);
        let usuario = this.authService.usuario;
        let proceso = this.authService.proceso;
        this.router.navigate(['/colaboradores']);
        swal.fire(`${proceso.nombre}`, `Hola ${usuario.nombre}, has iniciado sesión con éxito!`, 'success');
      }, err => {
        if (err.status == 400) {
          swal.fire('Error en iniciar sesión', 'Usuario o clave incorrecta.', 'error');
        }
      }
    );
  }

}
