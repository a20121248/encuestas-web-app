import { Component, OnInit, Input } from '@angular/core';
import { Empresa } from 'src/app/shared/models/empresa';
import { EmpresaService } from 'src/app/shared/services/empresa.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})

export class EmpresaComponent implements OnInit {
  @Input() lstEmpresas: Empresa[];
  dcEmpresa = ['nombre', 'porcentaje', 'ingresar'];

  constructor(
    private empresaService: EmpresaService,
    private router: Router) { }

  ngOnInit() {
    /*this.empresaService.getEmpresas().subscribe(empresas => {
      this.lstEmpresas = empresas;
    });*/
  }

  getTotalPorcentaje() {
    return this.lstEmpresas.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }

  revisarEmpresa(codigo: string): boolean {
    if (codigo == "1" || codigo == "2") return true;
    else return false;
  }

  irMasDetalle(empresa: Empresa) {
    if (empresa.nombre.toUpperCase().includes("EPS")) {
      this.router.navigateByUrl("/encuestas/eps");
    }
    if (empresa.nombre.toUpperCase().includes("PGA")) {
      this.router.navigateByUrl("/encuestas/centro");
    }
  }
}
