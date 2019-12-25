import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Area } from 'src/app/shared/models/area';
import { AreaService } from 'src/app/shared/services/area.service';
import { MatTable } from '@angular/material/table';
import { MatDialog, MatDialogRef, MatDialogConfig } from '@angular/material/dialog';
import { ModalCrearComponent } from '../../components/modal-crear/modal-crear.component';
import { ModalEditarComponent } from '../../components/modal-editar/modal-editar.component';
import { ModalEliminarComponent } from '../../components/modal-eliminar/modal-eliminar.component';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Campo } from 'src/app/shared/models/campo';
import swal from 'sweetalert2';

@Component({
  selector: 'app-areas',
  templateUrl: './areas.component.html',
  styleUrls: ['./areas.component.scss']
})
export class AreasComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  areas: Area[];
  selectedIndex: number;
  selectedArea: Area;
  dcAreas = ['codigo', 'nombre', 'division', 'fechaCreacion', 'fechaActualizacion', 'fechaEliminacion'];
  crearDialogRef: MatDialogRef<ModalCrearComponent>;
  editarDialogRef: MatDialogRef<ModalEditarComponent>;
  eliminarDialogRef: MatDialogRef<ModalEliminarComponent>;  
  subscribeAreas: Subscription;
  subscribeEliminar: Subscription;
  @ViewChild(MatTable, { static: false }) table: MatTable<any>;
  campoCodigo: Campo;
  campoNombre: Campo;
  campoDivision: Campo;

  constructor(private titleService: Title,
              private areaService: AreaService,
              public dialog: MatDialog) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'ÁREAS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Áreas');
    this.subscribeAreas = this.areaService.findAll().subscribe(areas => {
      this.areas = areas;
    });

    this.campoCodigo = new Campo();
    this.campoCodigo.name = 'codigo';
    this.campoCodigo.label = 'Código';
    this.campoCodigo.type = 'text';
    this.campoCodigo.width = 15;
    this.campoCodigo.maxLength = 7;
    this.campoCodigo.messages = ['Ingrese un código'];

    this.campoNombre = new Campo();
    this.campoNombre.name = 'nombre';
    this.campoNombre.label = 'Nombre';
    this.campoNombre.type = 'text';
    this.campoNombre.width = 80;
    this.campoNombre.maxLength = 200;
    this.campoNombre.messages = ['Ingrese un nombre'];

    this.campoDivision = new Campo();
    this.campoDivision.name = 'division';
    this.campoDivision.label = 'División';
    this.campoDivision.type = 'text';
    this.campoDivision.width = 100;
    this.campoDivision.maxLength = 200;
    this.campoDivision.messages = ['Ingrese la división a la que pertenece'];
  }

  ngOnDestroy(): void {
    this.subscribeAreas.unsubscribe();
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
    this.campoCodigo.value = "";
    this.campoNombre.value = "";
    this.campoDivision.value = "";

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Crear área`,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoDivision]
      ]
    };
    this.crearDialogRef = this.dialog.open(ModalCrearComponent, dialogConfig);
    this.crearDialogRef.afterClosed().pipe(filter(area => area)).subscribe(area => {
      this.areaService.create(area).subscribe((response) => {
        this.areas.push(response);
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  editar(): void {
    if (this.selectedArea == null) {
      swal.fire('Editar área', 'Por favor, seleccione un área.', 'error');
      return;
    }

    this.campoCodigo.value = this.selectedArea.codigo;
    this.campoNombre.value = this.selectedArea.nombre;
    this.campoDivision.value = this.selectedArea.division;

    const dialogConfig = new MatDialogConfig();
    dialogConfig.hasBackdrop = true;
    dialogConfig.width = '550px';
    dialogConfig.data = {
      titulo: `Editar área '${this.selectedArea.codigo}'`,
      item: this.selectedArea,
      camposArr: [
        [this.campoCodigo, this.campoNombre],
        [this.campoDivision]
      ]
    };
    this.editarDialogRef = this.dialog.open(ModalEditarComponent, dialogConfig);
    this.editarDialogRef.afterClosed().pipe(filter(area => area)).subscribe(area => {
      this.areaService.edit(area).subscribe((response) => {
        this.areas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    });
  }

  cambiarEstado() {
    if (this.selectedArea == null) {
      swal.fire('Deshabilitar área', 'Por favor, seleccione un área.', 'error');
    } else if (this.selectedArea.fechaEliminacion == null) {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.areaService.softDelete(this.selectedArea).subscribe(response => {
        console.log(response);
        this.areas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    } else {
      if (this.subscribeEliminar != null) {
        this.subscribeEliminar.unsubscribe();
      }
      this.subscribeEliminar = this.areaService.softUndelete(this.selectedArea).subscribe(response => {
        this.areas[this.selectedIndex] = response;
        this.table.renderRows();
      }, err => {
        console.log(err);
      });
    }
  }

  eliminar() {
    if (this.selectedArea == null) {
      swal.fire('Eliminar áreas', 'Por favor, seleccione un área.', 'error');
    } else {
      swal.fire({
        title: `Eliminar área '${this.selectedArea.codigo}'`,
        text: `¿Está seguro de eliminar el área '${this.selectedArea.nombre}'?`,
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
          this.subscribeEliminar = this.areaService.delete(this.selectedArea).subscribe(res => {
            this.areas.splice(this.selectedIndex, 1);
            this.table.renderRows();
          }, err => {
            console.log(err);
          }, () => {
            this.selectedIndex = -1;
            swal.fire(`Eliminar área '${this.selectedArea.codigo}'`, 'El área ha sido eliminada.', 'success');
            this.selectedArea = null;
          });
        }
      });
    }
  }

  limpiar(): void {
    swal.fire({
      title: `Eliminar áreas`,
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
        this.subscribeEliminar = this.areaService.deleteAll().subscribe(res => {
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar áreas`, 'Las áreas han sido eliminadas.', 'success');
        });
      }
    });
  }

  setSelected(area: Area, i: number) {
    this.selectedIndex = i;
    this.selectedArea = area;
  }
}
