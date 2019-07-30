
import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from "@angular/router";

//-------------------COMPONENTES LOCALES----------------------------------

import { Centro } from  '../../../../shared/models/centro';
import { CentroComponent } from '../../components/centro/centro.component';
import { CentroService } from 'src/app/shared/services/centro.service';


@Component({
  selector: 'app-enc-centro',
  templateUrl: './enc-centro.component.html',
  styleUrls: ['./enc-centro.component.css']
})
export class EncCentroComponent implements OnInit {
  lstCentros: Centro[] ;
  titulo = 'Herramienta de encuestas';

  constructor(
    private centroService: CentroService,
    private activatedRoute: ActivatedRoute,
    private location: Location ) { }

  @ViewChild(CentroComponent, {static: false})  centroComponent: CentroComponent ;

  ngOnInit() {
  }

  guardarEncuesta() {
    this.lstCentros = this.centroComponent.getLstCentro();
    this.centroService.postRespuesta (this.lstCentros);
  }
  goBack(){
    this.location.back();
  }
}
