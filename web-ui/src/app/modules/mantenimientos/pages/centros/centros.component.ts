import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Centro } from 'src/app/shared/models/centro';
import { CentroService } from 'src/app/shared/services/centro.service';
import { MatTable } from '@angular/material/table';
import { MatDialog, MatDialogRef, MatDialogConfig} from '@angular/material/dialog';
import { ModalCrearComponent } from '../../components/modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../../components/modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../../components/modal-eliminar/modal-eliminar.component';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Tipo } from 'src/app/shared/models/tipo';
import { Campo } from 'src/app/shared/models/campo';
import swal from 'sweetalert2';

@Component({
  selector: 'app-centros',
  templateUrl: './centros.component.html',
  styleUrls: ['./centros.component.scss']
})
export class CentrosComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  centros: Centro[];
  selectedIndex: number;
  selectedCentro: Centro;
  dcCentros = ['codigo', 'nombre', 'nivel', 'tipo', 'grupo', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeCentros: Subscription;
  subscribeCentrosTipos: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;
  campoNivel: Campo;
  campoTipo: Campo;
  campoGrupo: Campo;

  constructor(private titleService: Title,
              private centroService: CentroService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'CENTROS DE COSTOS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Centros de costos');
    this.subscribeCentros = this.centroService.findAll().subscribe(centros => {
      this.centros = centros;
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

    this.campoNivel = new Campo();
    this.campoNivel.name = 'nivel';
    this.campoNivel.label = 'Nivel';
    this.campoNivel.type = 'number';
    this.campoNivel.width = 15;
    this.campoNivel.items = {min: 1, max: 99};
    this.campoNivel.messages = ['Ingrese un nivel'];

    this.campoTipo = new Campo();
    this.campoTipo.name = 'tipo';
    this.campoTipo.label = 'Tipo de centro';
    this.campoTipo.type = 'select';
    this.campoTipo.width = 80;
    this.subscribeCentrosTipos = this.centroService.findAllTipos().subscribe(tipos => {
      this.campoTipo.items = tipos;
    }, err => {
      console.log(err);
    });
    this.campoTipo.value = null;
    this.campoTipo.messages = ['Seleccione un tipo de centro de costos'];
    
    this.campoGrupo = new Campo();
    this.campoGrupo.name = 'grupo';
    this.campoGrupo.label = 'Grupo';
    this.campoGrupo.type = 'text';
    this.campoGrupo.width = 100;
    this.campoGrupo.maxLength = 200;
    this.campoGrupo.messages = ['Ingrese un grupo de centro de costos'];
  }

  ngOnDestroy(): void {
    this.subscribeCentros.unsubscribe();
    this.subscribeCentrosTipos.unsubscribe();
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";
    this.campoNivel.value = "";
    this.campoTipo.value = null;
    this.campoGrupo.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear centro de costos`,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoNivel, this.campoTipo],
        [this.campoGrupo]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(centro => centro)).subscribe(centro => {
      this.centroService.create(centro).subscribe((response) => {
        this.centros.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedCentro == null) {
      swal.fire('Editar centro de costos', 'Por favor, seleccione un centro de costos.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedCentro.codigo;
    this.campoNombre.value = this.selectedCentro.nombre;
    this.campoNivel.value = this.selectedCentro.nivel;
    this.campoTipo.value = this.campoTipo.items.find(e => e.id == this.selectedCentro.tipo.id);
    this.campoGrupo.value = this.selectedCentro.grupo;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar centro de costos '${this.selectedCentro.codigo}'`,
      item: this.selectedCentro,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoNivel, this.campoTipo],
        [this.campoGrupo]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(centro => centro)).subscribe(centro => {
      this.centroService.edit(centro).subscribe((response) => {
        this.centros[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedCentro == null) {
      swal.fire('Deshabilitar centro', 'Por favor, seleccione un centro.', 'error');
    } else if (this.selectedCentro.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.centroService.softDelete(this.selectedCentro).subscribe(response => {
        this.centros[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.centroService.softUndelete(this.selectedCentro).subscribe(response => {
        this.centros[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedCentro == null) {
      swal.fire('Eliminar centro de costos', 'Por favor, seleccione un centro de costos.', 'error');
    } else {
      swal.fire({
        title: `Eliminar centro de costos '${this.selectedCentro.codigo}'`,
        text: `¿Está seguro de eliminar el centro de costos '${this.selectedCentro.nombre}'?`,
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
          this.subscribeEliminar = this.centroService.delete(this.selectedCentro).subscribe(res => {
            this.centros.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar centro de costos '${this.selectedCentro.codigo}'`, 'El centro de costos ha sido eliminado.', 'success');
            this.selectedCentro = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar centros de costos`,
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
        this.subscribeEliminar = this.centroService.deleteAll().subscribe(res => {
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar centros de costos`, 'Los centros de costos han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(centro: Centro, i: number) {
    this.selectedIndex = i;
    this.selectedCentro = centro;
  }
}
