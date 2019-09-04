import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Usuario } from '../../../../shared/models/usuario';
import { UsuarioService } from '../../../../shared/services/usuario.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';
import swal from 'sweetalert2';
import { Title } from '@angular/platform-browser';
import { Posicion } from 'src/app/shared/models/posicion';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';

@Component({
  selector: 'app-seleccionar-usuario',
  templateUrl: './seleccionar-usuario.component.html',
  styleUrls: ['./seleccionar-usuario.component.scss']
})
export class SeleccionarUsuarioComponent implements OnInit {

  dcUsuario = ['codigo', 'nombre', 'posicion', 'area', 'perfil', 'estado', 'ir'];
  lstUsuario: Usuario[];
  procesoActual: Proceso;
  posicion: Posicion;
  titulo: string;

  constructor(
    public authService: AuthService,
    private usuarioService: UsuarioService,
    private posicionService: PosicionService,
    private procesoService: ProcesoService,
    private router: Router,
    private titleService: Title
  ) {
    this.titulo = 'Colaboradores';
    this.procesoService.getCurrentProceso().subscribe(pro => {
      this.procesoActual = pro;
      this.authService.setProceso(pro);
      this.posicionService.findByProcesoIdAndUsuarioCodigo(this.procesoActual.id, this.authService.usuario.codigo).subscribe(pos => {
        this.authService.usuario.posicion = pos;
        this.usuarioService.getUsuariosDependientes(this.procesoActual.id, this.authService.usuario.posicion.codigo).subscribe(usuarios => {
          this.lstUsuario = usuarios as Usuario[];
          console.log(this.lstUsuario);
        });
      });
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Colaboradores');
    if (this.procesoActual != null && this.authService.usuario.posicion != null) {

    } else {
      console.log('error');
    }
  }
}
