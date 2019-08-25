import { Component, OnInit } from '@angular/core';
import { Centro } from 'src/app/shared/models/centro';
import { Title } from '@angular/platform-browser';
import { CentroService } from 'src/app/shared/services/centro.service';

@Component({
  selector: 'app-centros',
  templateUrl: './centros.component.html',
  styleUrls: ['./centros.component.scss']
})
export class CentrosComponent implements OnInit {
  tituloPagina: string;
  titulo: string;
  centros: Centro[];
  selectedCentro: Centro;
  dcCentros = ['codigo', 'nombre', 'nivel', 'tipo', 'grupo', 'fechaCreacion', 'fechaActualizacion'];

  constructor(private titleService: Title,
              private centroService: CentroService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE CENTROS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Centros');
    this.centroService.findAll().subscribe(centros => {
      this.centros = centros;
    });
  }

  crear(): void {
  }

  editar(): void {
  }

  eliminar(): void {
  }

  setSelected(centro: Centro) {
    this.selectedCentro = centro;
  }
}
