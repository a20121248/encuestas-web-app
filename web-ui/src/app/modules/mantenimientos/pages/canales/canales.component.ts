import { Component, OnInit } from '@angular/core';
import { Objeto } from 'src/app/shared/models/objeto';
import { Title } from '@angular/platform-browser';
import { CanalService } from 'src/app/shared/services/canal.service';

@Component({
  selector: 'app-canales',
  templateUrl: './canales.component.html',
  styleUrls: ['./canales.component.scss']
})
export class CanalesComponent implements OnInit {
  tituloPagina: string;
  titulo: string;
  canales: Objeto[];
  selectedCanal: Objeto;
  dcCanales = ['codigo', 'nombre', 'fechaCreacion', 'fechaActualizacion'];

  constructor(private titleService: Title,
              private canalService: CanalService) {
    this.tituloPagina = 'MANTENIMIENTO';
    this.titulo = 'LISTADO DE CANALES';
  }

  ngOnInit() {
    this.titleService.setTitle('Mantenimiento | Canales');
    this.canalService.findAll().subscribe(canales => {
      this.canales = canales;
    });
  }

  setSelected(canal: Objeto) {
    this.selectedCanal = canal;
  }
}
