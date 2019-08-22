import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/Proceso';
import { PerfilService } from 'src/app/shared/services/perfil.service';

@Component({
  selector: 'app-cargar-perfiles',
  templateUrl: './cargar-perfiles.component.html',
  styleUrls: ['./cargar-perfiles.component.css']
})
export class CargarPerfilesComponent implements OnInit {
  titulo: string;
  cantPerfiles: number;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;

  constructor(private perfilService: PerfilService) {
    this.titulo = 'CARGAR PERFILES';
    this.perfilService.count().subscribe(cantPerfiles => {
      this.cantPerfiles = cantPerfiles;
    });
  }

  ngOnInit() {
  }

}
