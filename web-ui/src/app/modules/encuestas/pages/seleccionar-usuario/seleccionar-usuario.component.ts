import { Component, OnInit, OnDestroy } from '@angular/core';
import { Usuario } from '../../../../shared/models/usuario';
import { UsuarioService } from '../../../../shared/services/usuario.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Title } from '@angular/platform-browser';
import { Posicion } from 'src/app/shared/models/posicion';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-seleccionar-usuario',
  templateUrl: './seleccionar-usuario.component.html',
  styleUrls: ['./seleccionar-usuario.component.scss']
})
export class SeleccionarUsuarioComponent implements OnInit, OnDestroy {
  dcUsuario = ['codigo', 'nombre', 'posicion', 'area', 'perfil', 'estado', 'ir'];
  lstUsuario: Usuario[];
  posicion: Posicion;
  titulo: string;
  subscribeProceso: Subscription;
  subscribePosicion: Subscription;

  constructor(
    public authService: AuthService,
    private usuarioService: UsuarioService,
    private posicionService: PosicionService,
    private procesoService: ProcesoService,
    private router: Router,
    private titleService: Title
  ) {
    this.titulo = 'Colaboradores';
    this.subscribeProceso = this.procesoService.getCurrentProceso().subscribe(
      pro => {
        if (pro != null) {
          this.authService.setProceso(pro);
          const usuarioCodigo = this.authService.usuario.codigo;
          if (usuarioCodigo != null) {
            this.subscribePosicion = this.posicionService.findByProcesoIdAndUsuarioCodigo(pro.id, usuarioCodigo).subscribe(pos => {
              if (pos != null) {
                this.authService.usuario.posicion = pos;
                this.usuarioService.getUsuariosDependientes(pro.id, this.authService.usuario.posicion.codigo).subscribe(usu => {
                  this.lstUsuario = usu as Usuario[];
                });
              }
            });
          }
        }
      }, err => {
        console.log(err);
      }
    );
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Colaboradores');
  }

  ngOnDestroy(): void {
    if (this.subscribeProceso != null) {
      this.subscribeProceso.unsubscribe();
    }
    if (this.subscribePosicion != null) {
      this.subscribePosicion.unsubscribe();
    }
  }
}
