import { AuthService } from 'src/app/shared/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Usuario } from 'src/app/shared/models/usuario';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  titulo = 'Iniciar sesión';
  usuario: Usuario;
  hide = true;

  dominios: string[] = ['Generales', 'Vida'];

  loginForm: FormGroup;

  constructor(
    public authService: AuthService,
    private router: Router,
    private titleService: Title,
    private fb: FormBuilder) {
    this.usuario = new Usuario();
  }

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      swal.fire('Login', `Ya iniciaste sesión anteriormente.`, 'info');
      this.router.navigate(['/colaboradores']);
    }
    this.titleService.setTitle('Encuestas | Login');
    this.loginForm = this.fb.group({
      codigo: [null, Validators.required],
      contrasenha: [null, Validators.required],
      dominio: [null, Validators.required]
    });
  }

  get codigo() { return this.loginForm.get('codigo'); }
  get contrasenha() { return this.loginForm.get('contrasenha'); }
  get dominio() { return this.loginForm.get('dominio'); }

  isFieldValid(field: string) {
    return !this.loginForm.get(field).valid && this.loginForm.get(field).touched;
  }

  login(): void {
    if (this.loginForm.valid) {
      this.usuario.codigo = this.codigo.value;
      this.usuario.contrasenha = this.contrasenha.value;
      if (this.usuario.codigo != 'admin.encuestas' && this.dominio.value == 'Generales') {
        this.usuario.codigo = 'epps\\' + this.usuario.codigo;
      }

      this.authService.login(this.usuario).subscribe(
        response => {
          //console.log(response);
          //let payload = JSON.parse(atob(response.access_token.split('.')[1]));
          //console.log(payload);

          this.authService.guardarUsuario(response.access_token);
          this.authService.guardarToken(response.access_token);
          this.router.navigate(['/colaboradores']);
          swal.fire(`Encuestas PPTO`, `Has iniciado sesión con éxito.`, 'success');
        }, err => {
          console.log(err);
          if (err.status == 400) {
            swal.fire('Error en iniciar sesión',
                      err.error.error_description,
                      'error');
          } else if (err.status == 401) {
            swal.fire('Error en iniciar sesión',
                      err.error.error_description,
                      'error');
          } else {
            swal.fire('Error en iniciar sesión',
                      'No hay respuesta del servidor. Contactar con Helpdesk.',
                      'error');
          }
        }
      );
    } else {
      this.loginForm.get('codigo').markAsTouched({ onlySelf: true });
      this.loginForm.get('contrasenha').markAsTouched({ onlySelf: true });
      this.loginForm.get('dominio').markAsTouched({ onlySelf: true });
    }
  }
}
