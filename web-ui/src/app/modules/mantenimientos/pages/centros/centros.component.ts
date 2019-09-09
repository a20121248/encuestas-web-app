import { Component, OnInit, OnDestroy } from '@angular/core';
import { Centro } from 'src/app/shared/models/centro';
import { Title } from '@angular/platform-browser';
import { CentroService } from 'src/app/shared/services/centro.service';
import swal from 'sweetalert2';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-centros',
  templateUrl: './centros.component.html',
  styleUrls: ['./centros.component.scss']
})
export class CentrosComponent implements OnInit, OnDestroy {
  tituloPagina: string;
  titulo: string;
  centros: Centro[];
  selectedCentro: Centro;
  dcCentros = ['codigo', 'nombre', 'nivel', 'tipo', 'grupo', 'fechaCreacion', 'fechaActualizacion'];
  subscribeCentros: Subscription;
  subscribeEliminar: Subscription;

  constructor(private titleService: Title,
              private centroService: CentroService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'CENTROS DE COSTOS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Centros de costos');
    this.subscribeCentros = this.centroService.findAll().subscribe(centros => {
      this.centros = centros;
    });
  }

  ngOnDestroy(): void {
    this.subscribeCentros.unsubscribe();
    if (this.subscribeEliminar != null) {
      this.subscribeEliminar.unsubscribe();
    }
  }

  crear(): void {
  }

  editar(): void {
  }

  eliminar(): void {
  }

  eliminarTodo(): void {
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
          console.log(res);
        }, err => {
          console.log(err);
        }, () => {
          swal.fire(`Eliminar centros de costos`, 'Los centros de costos han sido eliminados.', 'success');
        });
      }
    });
  }

  setSelected(centro: Centro) {
    this.selectedCentro = centro;
  }
}
