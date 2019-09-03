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

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit() {
  }

  logout(): void {
    this.authService.logout();
    swal.fire('Login', `Has cerrado sesión con éxito.`, 'success');
    this.router.navigate(['/login']);
  }
}
