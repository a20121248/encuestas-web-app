import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';

import { LineaCanalService } from 'src/app/shared/services/linea-canal.service';
import { LineaCanalComponent } from '../../components/linea-canal/linea-canal.component';
import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { Linea } from 'src/app/shared/models/linea';
import { Canal } from 'src/app/shared/models/canal';

@Component({
  selector: 'app-enc-linea-canal',
  templateUrl: './enc-linea-canal.component.html',
  styleUrls: ['./enc-linea-canal.component.css']
})
export class EncLineaCanalComponent implements OnInit {
  lstLineaCanal: LineaCanal[];
  lstVertical: Linea[];
  lstHorizontal: Canal[];
  
  titulo = 'Herramienta de encuestas';

  constructor(
    private lineaCanalService: LineaCanalService,
    private location:Location
    ) { }

  @ViewChild(LineaCanalComponent, {static: false})  lineaCanalComponent: LineaCanalComponent ;

  ngOnInit() {
  }

  guardarEncuesta(){
    this.lstLineaCanal = this.lineaCanalComponent.getLstLineaCanal();
    this.lineaCanalService.postRespuesta (this.lstLineaCanal);
  }

  goBack(){
    this.location.back();
  }
}
