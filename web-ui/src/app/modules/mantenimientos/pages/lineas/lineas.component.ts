import { Component, OnInit } from '@angular/core';
import { Objeto } from 'src/app/shared/models/objeto';
import { Title } from '@angular/platform-browser';
import { LineaService } from 'src/app/shared/services/linea.service';

@Component({
  selector: 'app-lineas',
  templateUrl: './lineas.component.html',
  styleUrls: ['./lineas.component.scss']
})
export class LineasComponent implements OnInit {
  tituloPagina: string;
  titulo: string;
  lineas: Objeto[];
  selectedLinea: Objeto;
  dcLineas = ['codigo', 'nombre', 'fechaCreacion', 'fechaActualizacion'];

  constructor(private titleService: Title,
              private lineaService: LineaService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE LÍNEAS';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Líneas');
    this.lineaService.findAll().subscribe(lineas => {
      this.lineas = lineas;
    });
  }

  crear(): void {
  }

  editar(): void {
  }

  eliminar(): void {
  }

  setSelected(linea: Objeto) {
    this.selectedLinea = linea;
  }
}
