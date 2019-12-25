import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Objeto } from 'src/app/shared/models/objeto';
import { SubcanalService } from 'src/app/shared/services/subcanal.service';
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
  selector: 'app-subcanales',
  templateUrl: './subcanales.component.html',
  styleUrls: ['./subcanales.component.scss']
})
export class SubcanalesComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  subcanales: Objeto[];
  canales: Objeto[];
  selectedIndex: number;
  selectedSubcanal: Objeto;
  dcSubcanales = ['codigo', 'nombre', 'canalCodigo', 'canalNombre', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;
  subscribeSubcanales: Subscription;
  subscribeCanales: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;
  campoCanal: Campo;

  constructor(private titleService: Title,
              private subcanalService: SubcanalService,
              private canalService: CanalService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE SUBCANALES';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Subcanales');
    this.subcanalService.findAll().subscribe(subcanales => {
      this.subcanales = subcanales;
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

    this.campoCanal = new Campo();
    this.campoCanal.name = 'objetoPadre';
    this.campoCanal.label = 'Canal';
    this.campoCanal.type = 'select';
    this.campoCanal.width = 100;
    this.subscribeCanales = this.canalService.findAll().subscribe(canales => {
      this.campoCanal.items = canales;
    }, err => {
      console.log(err);
    });
    this.campoCanal.value = null;
    this.campoCanal.messages = ['Seleccione el canal al que pertenece'];
  }

  ngOnDestroy(): void {
    if (this.subscribeSubcanales) this.subscribeSubcanales.unsubscribe();
    if (this.subscribeCanales) this.subscribeCanales.unsubscribe();
    if (this.subscribeEliminar) this.subscribeEliminar.unsubscribe();
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";
    this.campoCanal.value = null;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear subcanal`,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoCanal]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(subcanal => subcanal)).subscribe(subcanal => {
      this.subcanalService.create(subcanal).subscribe((response) => {
        this.subcanales.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedSubcanal == null) {
      swal.fire('Editar subcanal', 'Por favor, seleccione un subcanal.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedSubcanal.codigo;
    this.campoNombre.value = this.selectedSubcanal.nombre;
    this.campoCanal.value = this.campoCanal.items.find(e => e.id == this.selectedSubcanal.objetoPadre.id);

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar subcanal '${this.selectedSubcanal.codigo}'`,
      item: this.selectedSubcanal,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoCanal]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(subcanal => subcanal)).subscribe(subcanal => {
      this.subcanalService.edit(subcanal).subscribe((response) => {
        this.subcanales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedSubcanal == null) {
      swal.fire('Deshabilitar subcanal', 'Por favor, seleccione un subcanal.', 'error');
    } else if (this.selectedSubcanal.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.subcanalService.softDelete(this.selectedSubcanal).subscribe(response => {
        this.subcanales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.subcanalService.softUndelete(this.selectedSubcanal).subscribe(response => {
        this.subcanales[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedSubcanal == null) {
      swal.fire('Eliminar subcanal', 'Por favor, seleccione un subcanal.', 'error');
    } else {
      swal.fire({
        title: `Eliminar subcanal '${this.selectedSubcanal.codigo}'`,
        text: `¿Está seguro de eliminar el subcanal '${this.selectedSubcanal.nombre}'?`,
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
          this.subscribeEliminar = this.subcanalService.delete(this.selectedSubcanal).subscribe(res => {
            this.subcanales.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar subcanal '${this.selectedSubcanal.codigo}'`, 'El subcanal ha sido eliminado.', 'success');
            this.selectedSubcanal = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar subcanales`,
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
        this.subscribeEliminar = this.subcanalService.deleteAll().subscribe(res => {
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar subcanales`, 'Los subcanales han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(subcanal: Objeto, i: number) {
    this.selectedIndex = i;
    this.selectedSubcanal = subcanal;
  }
}
