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
    if (codigo == '1' || codigo == '2') {
      return true;
    }
    return false;
  }

  irMasDetalle(empresa: Empresa) {
    if (empresa.nombre.toUpperCase().includes('PACÍFICO EPS')) {
      this.router.navigate(['eps'], { relativeTo: this.route });
    } else if (empresa.nombre.toUpperCase().includes('PACÍFICO SEGUROS')) {
      this.router.navigate(['centro'], { relativeTo: this.route });
    }
  }
}
