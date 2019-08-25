import { Component, OnInit } from '@angular/core';
import { Objeto } from 'src/app/shared/models/objeto';
import { Title } from '@angular/platform-browser';
import { SubcanalService } from 'src/app/shared/services/subcanal.service';

@Component({
  selector: 'app-subcanales',
  templateUrl: './subcanales.component.html',
  styleUrls: ['./subcanales.component.css']
})
export class SubcanalesComponent implements OnInit {
  tituloPagina: string;
  titulo: string;
  subcanales: Objeto[];
  selectedSubcanal: Objeto;
  dcSubcanales = ['codigo', 'nombre', 'canalCodigo', 'canalNombre', 'fechaCreacion', 'fechaActualizacion'];

  constructor(private titleService: Title,
              private subcanalService: SubcanalService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE SUBCANALES';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Subcanales');
    this.subcanalService.findAll().subscribe(subcanales => {
      this.subcanales = subcanales;
    });
  }

  setSelected(subcanal: Objeto) {
    this.selectedSubcanal = subcanal;
  }
}
