import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Linea } from 'src/app/shared/models/linea';
import { LineaService } from 'src/app/shared/services/linea.service';
import { Usuario } from 'src/app/shared/models/usuario';

@Component({
  selector: 'app-form-linea',
  templateUrl: './linea.component.html',
  styleUrls: ['./linea.component.scss']
})
export class LineaComponent implements OnInit {
  @Input() lstLineas: Linea[];
  @Input() usuario: Usuario;

  dcLinea = ['codigo', 'nombre', 'porcentaje', 'completar'];
  url: string;

  constructor() {
  }

  ngOnInit() {
    console.log(this.usuario.posicion.perfil);
    this.url = this.revisarPerfil(this.usuario.posicion.perfil.perfilTipo.id);
  }

  revisarPerfil(perfilTipoId: number): string {
    if (perfilTipoId == 2) { // Es una Linea, ir a Producto-Canal
      return '/producto-canal';
    } else  if (perfilTipoId == 3) { // Es un Canal, ir a Producto-Subcanal
      return `/12/producto-subcanal`;
    }
  }

  getTotalPorcentaje() {
    if (this.lstLineas != null) {
      return this.lstLineas
        .map(t => t.porcentaje)
        .reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }
}
