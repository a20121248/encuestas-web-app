import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Location } from "@angular/common";

import { Eps } from '../../../../shared/models/eps';
import { EpsComponent } from '../../components/eps/eps.component';
import { EpsService } from 'src/app/shared/services/eps.service';

@Component({
  selector: 'app-enc-eps',
  templateUrl: './enc-eps.component.html',
  styleUrls: ['./enc-eps.component.css']
})
export class EncEPSComponent implements OnInit {
  lstEps: Eps[] ;
  titulo = 'Herramienta de encuestas';
  constructor(
    private epsService: EpsService,
    private location: Location) { }

  @ViewChild(EpsComponent, {static: false})  epsComponent: EpsComponent ;

  ngOnInit() {
  }

  guardarEncuesta(){
    this.lstEps = this.epsComponent.getLstEps();
    this.epsService.postRespuesta (this.lstEps);
  }
  goBack(){
    this.location.back();
  }
}
