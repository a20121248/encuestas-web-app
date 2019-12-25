import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Usuario } from 'src/app/shared/models/usuario';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { MatTable } from '@angular/material/table';
import { MatDialog, MatDialogRef, MatDialogConfig} from '@angular/material/dialog';
import { ModalCrearComponent } from '../../components/modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../../components/modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../../components/modal-eliminar/modal-eliminar.component';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Campo } from 'src/app/shared/models/campo';
import swal from 'sweetalert2';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  usuarios: Usuario[];
  selectedIndex: number;
  selectedUsuario: Usuario;
  dcUsuarios = ['codigo', 'usuarioVida', 'usuarioGenerales', 'nombreCompleto', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeUsuarios: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombreCompleto: Campo;
  campoUsuarioVida: Campo;
  campoUsuarioGenerales: Campo;

  constructor(private titleService: Title,
              private usuarioService: UsuarioService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'COLABORADORES';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Colaboradores');
    this.subscribeUsuarios = this.usuarioService.findAll().subscribe(usuarios => {
      this.usuarios = usuarios;
    });

    this.campoCodigo = new Campo();
    this.campoCodigo.name = 'codigo';
    this.campoCodigo.label = 'Matrícula';
    this.campoCodigo.type = 'text';
    this.campoCodigo.width = 15;
    this.campoCodigo.maxLength = 8;
    this.campoCodigo.messages = ['Ingrese la matrícula'];

    this.campoNombreCompleto = new Campo();
    this.campoNombreCompleto.name = 'nombreCompleto';
    this.campoNombreCompleto.label = 'Nombre';
    this.campoNombreCompleto.type = 'text';
    this.campoNombreCompleto.width = 80;
    this.campoNombreCompleto.maxLength = 200;
    this.campoNombreCompleto.messages = ['Ingrese el nombre del colaborador'];

    this.campoUsuarioVida = new Campo();
    this.campoUsuarioVida.name = 'usuarioVida';
    this.campoUsuarioVida.label = 'Usuario de red de Vida';
    this.campoUsuarioVida.type = 'text';
    this.campoUsuarioVida.width = 47.5;
    this.campoUsuarioVida.maxLength = 50;
    this.campoUsuarioVida.messages = ['Ingrese el usuario de red de Vida'];

    this.campoUsuarioGenerales = new Campo();
    this.campoUsuarioGenerales.name = 'usuarioGenerales';
    this.campoUsuarioGenerales.label = 'Usuario de red de Generales';
    this.campoUsuarioGenerales.type = 'text';
    this.campoUsuarioGenerales.width = 47.5;
    this.campoUsuarioGenerales.maxLength = 50;
    this.campoUsuarioGenerales.messages = ['Ingrese el usuario de red de Generales'];
  }

  ngOnDestroy(): void {
    this.subscribeUsuarios.unsubscribe();
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombreCompleto.value = "";
    this.campoUsuarioVida.value = "";
    this.campoUsuarioGenerales.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear colaborador`,
      camposArr: [
        [this.campoCodigo, this.campoNombreCompleto],
        [this.campoUsuarioVida, this.campoUsuarioGenerales]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(usuario => usuario)).subscribe(usuario => {
      this.usuarioService.create(usuario).subscribe((response) => {
        this.usuarios.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedUsuario == null) {
      swal.fire('Editar colaborador', 'Por favor, seleccione un colaborador.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedUsuario.codigo;
    this.campoNombreCompleto.value = this.selectedUsuario.nombreCompleto;
    this.campoUsuarioVida.value = this.selectedUsuario.usuarioVida ? this.selectedUsuario.usuarioVida : "";
    this.campoUsuarioGenerales.value = this.selectedUsuario.usuarioGenerales ? this.selectedUsuario.usuarioGenerales : "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar colaborador '${this.selectedUsuario.codigo}'`,
      item: this.selectedUsuario,
      camposArr: [
        [this.campoCodigo, this.campoNombreCompleto],
        [this.campoUsuarioVida, this.campoUsuarioGenerales]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(usuario => usuario)).subscribe(usuario => {
      this.usuarioService.edit(usuario).subscribe((response) => {
        this.usuarios[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedUsuario == null) {
      swal.fire('Deshabilitar colaborador', 'Por favor, seleccione un colaborador.', 'error');
    } else if (this.selectedUsuario.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.usuarioService.softDelete(this.selectedUsuario).subscribe(response => {
        this.usuarios[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.usuarioService.softUndelete(this.selectedUsuario).subscribe(response => {
        this.usuarios[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedUsuario == null) {
      swal.fire('Eliminar colaborador', 'Por favor, seleccione un colaborador.', 'error');
    } else {
      swal.fire({
        title: `Eliminar colaborador '${this.selectedUsuario.codigo}'`,
        text: `¿Está seguro de eliminar el colaborador '${this.selectedUsuario.nombreCompleto}'?`,
        type: 'warning',
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar'
      }).then((result) => {
        if (result.value) {
          if (this.subscribeEliminar != null) {
            this.subscribeEliminar.unsubscribe();
          }
          this.subscribeEliminar = this.usuarioService.delete(this.selectedUsuario).subscribe(res => {
            this.usuarios.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            this.selectedUsuario = null;
            swal.fire(`Eliminar colaborador '${this.selectedUsuario.codigo}'`, 'El colaborador ha sido eliminado.', 'success');
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar colaboradores`,
      text: 'Esta acción es irreversible.',
      type: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar'
    }).then((result) => {
      if (result.value) {
        if (this.subscribeEliminar != null) {
          this.subscribeEliminar.unsubscribe();
        }
        this.subscribeEliminar = this.usuarioService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar colaboradores`, 'Los colaboradores han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(usuario: Usuario, i: number) {
    this.selectedIndex = i;
    this.selectedUsuario = usuario;
  }
}
