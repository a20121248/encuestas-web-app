import { Component, OnInit } from '@angular/core';
import { Empresa } from '../../../../shared/models/empresa';
import { EmpresaComponent } from '../../components/empresa/empresa.component';
import { ViewChild } from '@angular/core';
import { EmpresaService } from 'src/app/shared/services/empresa.service';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-enc-empresa',
  templateUrl: './enc-empresa.component.html',
  styleUrls: ['./enc-empresa.component.css']
})
export class EncEmpresaComponent implements OnInit {
  lstEmpresas: Empresa[];
  titulo = 'Herramienta de encuestas';
  usuarioCodigo: string;

  constructor(private empresaService: EmpresaService, private _activatedRoute:ActivatedRoute) { }

  @ViewChild(EmpresaComponent, {static: false})  empresaComponent: EmpresaComponent ;

  ngOnInit() {
    this.usuarioCodigo = this._activatedRoute.snapshot.paramMap.get('codigo');
  }

  guardarEncuesta(){
    this.lstEmpresas = this.empresaComponent.getLstEmpresas();
    this.empresaService.postRespuesta (this.lstEmpresas);
  }
}
