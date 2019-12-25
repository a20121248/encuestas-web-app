import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Objeto } from 'src/app/shared/models/objeto';
import { CanalService } from 'src/app/shared/services/canal.service';
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
  selector: 'app-canales',
  templateUrl: './canales.component.html',
  styleUrls: ['./canales.component.scss']
})
export class CanalesComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  canales: Objeto[];
  selectedIndex: number;
  selectedCanal: Objeto;
  dcCanales = ['codigo', 'nombre', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeCanales: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;

  constructor(private titleService: Title,
              private canalService: CanalService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE CANALES';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Canales');
    this.canalService.findAll().subscribe(canales => {
      this.canales = canales;
    });

    this.campoCodigo = new Campo();
    this.campoCodigo.name = 'codigo';
    this.campoCodigo.label = 'Código';
    this.campoCodigo.type = 'text';
    this.campoCodigo.width = 15;
    this.campoCodigo.maxLength = 8;
    this.campoCodigo.messages = ['Ingrese un código'];

    this.campoNombre = new Campo();
    this.campoNombre.name = 'nombre';
    this.campoNombre.label = 'Nombre';
    this.campoNombre.type = 'text';
    this.campoNombre.width = 80;
    this.campoNombre.maxLength = 200;
    this.campoNombre.messages = ['Ingrese un nombre'];
  }

  ngOnDestroy(): void {
    if (this.subscribeCanales) this.subscribeCanales.unsubscribe();
    if (this.subscribeEliminar) this.subscribeEliminar.unsubscribe();
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear canal`,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(canal => canal)).subscribe(canal => {
      this.canalService.create(canal).subscribe((response) => {
        this.canales.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedCanal == null) {
      swal.fire('Editar canal', 'Por favor, seleccione un canal.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedCanal.codigo;
    this.campoNombre.value = this.selectedCanal.nombre;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar canal '${this.selectedCanal.codigo}'`,
      item: this.selectedCanal,
      camposArr: [
        [this.campoCodigo, this.campoNombre]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(canal => canal)).subscribe(canal => {
      this.canalService.edit(canal).subscribe((response) => {
        this.canales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedCanal == null) {
      swal.fire('Deshabilitar canal', 'Por favor, seleccione un canal.', 'error');
    } else if (this.selectedCanal.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.canalService.softDelete(this.selectedCanal).subscribe(response => {
        this.canales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.canalService.softUndelete(this.selectedCanal).subscribe(response => {
        this.canales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedCanal == null) {
      swal.fire('Eliminar canal', 'Por favor, seleccione un canal.', 'error');
    } else {
      swal.fire({
        title: `Eliminar canal '${this.selectedCanal.codigo}'`,
        text: `¿Está seguro de eliminar el canal '${this.selectedCanal.nombre}'?`,
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
          this.subscribeEliminar = this.canalService.delete(this.selectedCanal).subscribe(res => {
            this.canales.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar canal '${this.selectedCanal.codigo}'`, 'El canal ha sido eliminado.', 'success');
            this.selectedCanal = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar canales`,
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
        this.subscribeEliminar = this.canalService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar canales`, 'Los canales han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(canal: Objeto, i: number) {
    this.selectedIndex = i;
    this.selectedCanal = canal;
  }
}
