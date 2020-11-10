import { Component, OnInit, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { PosicionService } from 'src/app/shared/services/posicion.service';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Usuario } from 'src/app/shared/models/usuario';
import { Subscription } from 'rxjs';
import swal from 'sweetalert2';

@Component({
  selector: 'app-enc-replicar',
  templateUrl: './enc-replicar.component.html',
  styleUrls: ['./enc-replicar.component.scss']
})
export class EncReplicarComponent implements OnInit, OnDestroy {
  titulo: string;
  usuariosOrigen: Usuario[];
  selectedUsuarioOrigen: Usuario;
  usuariosDestino: Usuario[] = [];
  selectedUsuariosDestino = [];
  subscribeProceso: Subscription;
  subscribePosicion: Subscription;
  subscribeReplicas: Subscription;

  constructor(
    public authService: AuthService,
    private usuarioService: UsuarioService,
    private posicionService: PosicionService,
    private procesoService: ProcesoService,
    private router: Router,
    private titleService: Title
  ) {
    this.titulo = 'Replicar encuestas';
    this.subscribeProceso = this.procesoService.getCurrentProceso().subscribe(
      pro => {
        if (pro != null) {
          this.authService.setProceso(pro);
          const usuarioCodigo = this.authService.usuario.codigo;
          if (usuarioCodigo != null) {
            this.subscribePosicion = this.posicionService.findByProcesoIdAndUsuarioCodigo(pro.id, usuarioCodigo).subscribe(
              pos => {
                if (pos != null) {
                  this.authService.usuario.posicion = pos;
                  this.usuarioService.getUsuariosDependientesCompletados(pro.id, this.authService.usuario.posicion.codigo).subscribe(usu => {
                    this.usuariosOrigen = usu as Usuario[];
                  });
                }
              }, err => {
                console.log(err);
              }
            );
          }
        }
      }, err => {
        console.log(err);
      }
    );
  }

  updateUsers(usuarioSeleccionado: Usuario) {
    let procesoId = this.authService.proceso.id;
    let posicionCodigo = usuarioSeleccionado.posicion.codigo;
    let responsablePosicionCodigo = this.authService.usuario.posicion.codigo;
    let perfilId = usuarioSeleccionado.posicion.perfil.id;
    this.subscribeReplicas = this.usuarioService.getUsuariosDependientesReplicar(procesoId, posicionCodigo, responsablePosicionCodigo, perfilId).subscribe(
      (res) => {
        this.usuariosDestino = res;
        this.selectedUsuariosDestino = null;
      }, (err) => {
        console.log(err);
      });      
  }

  replicar() {
    let procesoId = this.authService.proceso.id;
    let posicionCodigo = this.selectedUsuarioOrigen.posicion.codigo;
    this.subscribeReplicas = this.usuarioService.replicar(procesoId, posicionCodigo, this.selectedUsuariosDestino).subscribe(
      (res) => {
        console.log(res);
      }, (err) => {
        console.log(err);
      }, () => {
        swal.fire(`Replicar`, `Se replicaron las encuestas a usuarios.`, 'success');
      });
  }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Replicar encuestas');
  }

  ngOnDestroy(): void {
    /*if (this.subscribeProceso != null) {
      this.subscribeProceso.unsubscribe();
    }
    if (this.subscribePosicion != null) {
      this.subscribePosicion.unsubscribe();
    }*/
  }
}
