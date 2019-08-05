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
  @Input() usuarioSeleccionado: Usuario;
  dcEmpresa = ['nombre', 'porcentaje', 'ingresar'];
  tipo = 'centro';

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
    if (codigo == '1') {
      this.tipo = 'centro';
      return true;
    } else if (codigo == '2') {
      this.tipo = 'eps';
      return true;
    }
    return false;
  }
}
