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
  selectFormControl = new FormControl('', Validators.required);

  selectedDominio: string;
  dominioControl = new FormControl('', [Validators.required]);
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
    this.titleService.setTitle('Encuestas | Login');
    this.loginForm = this.fb.group({
      codigo: new FormControl('', Validators.required),
      contrasenha: new FormControl('', Validators.required),
      dominio: new FormControl('', Validators.required)
    });
    if (this.authService.isAuthenticated()) {
      swal.fire('Login', `Hola ${this.authService.usuario.nombre}, ya iniciaste sesión anteriormente.`, 'info');
      this.router.navigate(['/colaboradores']);
    }
  }

  get codigo() { return this.loginForm.get('codigo'); }
  get contrasenha() { return this.loginForm.get('contrasenha'); }
  get dominio() { return this.loginForm.get('dominio'); }

  login(): void {
    if (this.codigo.value == '' || this.contrasenha.value == '') {
      swal.fire('Error en iniciar sesión', 'Matrícula o contraseña vacías.', 'error');
      return;
    }
    if (this.dominio.value == '') {
      swal.fire('Error en iniciar sesión', 'Seleccione un dominio.', 'error');
      return;
    }
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
        let usuario = this.authService.usuario;
        this.router.navigate(['/colaboradores']);
        swal.fire(`Encuestas PPTO`, `Hola ${usuario.nombre}, has iniciado sesión con éxito!`, 'success');
      }, err => {
        console.log(err);
        if (err.status == 400) {
          swal.fire('Error en iniciar sesión',
                    err.error.error_description,
                    'error');
        } else if (err.status == 401) {
          swal.fire('Error en iniciar sesión',
                    'No se pudo conectar al servicio de directorio activo. Contactar con Helpdesk.',
                    'error');
        };
      }
    );
  }

}
