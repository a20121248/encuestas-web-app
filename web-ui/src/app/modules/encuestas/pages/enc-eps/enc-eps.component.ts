import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';

//-------------------COMPONENTES LOCALES----------------------------------

import { Eps } from  '../../../../shared/models/eps';
import { EpsComponent } from '../../components/eps/eps.component';
import { EpsService } from 'src/app/shared/services/eps.service';

@Component({
  selector: 'app-enc-eps',
  templateUrl: './enc-eps.component.html',
  styleUrls: ['./enc-eps.component.css']
})
export class EncEPSComponent implements OnInit {
  lstEps: Eps[] ;

  constructor(private epsService:EpsService) { }

  @ViewChild(EpsComponent, {static: false})  epsComponent: EpsComponent ;

  ngOnInit() {
  }

  guardarEncuesta(){
    this.lstEps = this.epsComponent.getLstEps();
    this.epsService.postRespuesta (this.lstEps);  
  }
}
