import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Empresa } from 'src/app/shared/models/empresa';
import { Usuario } from 'src/app/shared/models/usuario';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})

export class EmpresaComponent implements OnInit {
  @Input() lstEmpresas: Empresa[];
  @Input() usuario: Usuario;
  dcEmpresa = ['nombre', 'porcentaje', 'ingresar'];
  url: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
  }

  getTotalPorcentaje() {
    if (this.lstEmpresas != null) {
      return this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }

  revisarEmpresa(codigo: string): boolean {
    let perfilTipoId = this.usuario.posicion.perfil.perfilTipo.id;
    if (codigo == '1') {
      if (perfilTipoId == 1) { // Perfil STAFF: Pagina de centros de costos
        this.url = 'centro';
        return true;
      } else if (perfilTipoId == 2) { // Perfil LINEA: Pagina de linea
        this.url = 'lineas';
        return true;
      } else if ([3, 4].includes(perfilTipoId)) { // Perfil Canal o Mixto: Pagina de linea-canal
        this.url = 'linea-canal';
        return true;
      }
    } else if (codigo == '2') {
      this.url = 'eps';
      return true;
    }
    return false;
  }
}
