import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-navegacion',
  templateUrl: './navegacion.component.html',
  styleUrls: ['./navegacion.component.scss']
})
export class NavegacionComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
  }

  logout(): void {
    let nombre = this.authService.usuario.nombre;
    this.authService.logout();
    swal.fire('Login', `Hola ${nombre}, ha cerrado sesión con éxito.`, 'success');
    this.router.navigate(['/login']);
  }
}
