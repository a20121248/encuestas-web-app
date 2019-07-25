import { Component, OnInit } from '@angular/core';

import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { LineaCanalService } from 'src/app/shared/services/linea-canal.service';
import { Linea } from 'src/app/shared/models/linea';
import { Canal } from 'src/app/shared/models/canal';
import { LineaService } from 'src/app/shared/services/linea.service';
import { CanalService } from 'src/app/shared/services/canal.service';

@Component({
  selector: 'app-linea-canal',
  templateUrl: './linea-canal.component.html',
  styleUrls: ['./linea-canal.component.css']
})
export class LineaCanalComponent implements OnInit {
  
  private lstLineaCanal: LineaCanal[] = [] ;
  private lstLinea: Linea[] =  [];
  private lstCanal: Canal[] = [];
  lstCabecera: string[]= ["nombreLinea"];
  constructor(
    private lineaCanalService: LineaCanalService,
    private lineaService: LineaService,
    private canalService: CanalService
  ) { }

  ngOnInit() {
    this.lineaService.getLinea().subscribe(linea => {
      this.lstLinea = linea;
      console.log(this.lstLinea);
    });
    this.canalService.getCanal().subscribe(canal => {
      this.lstCanal = canal;
      this.setUpCabecera();
      console.log(this.lstCabecera);
    });
    this.lineaCanalService.getLineaCanal().subscribe(lineaCanal=>{ 
      this.lstLineaCanal=lineaCanal;
    });
  }

  setUpCabecera():void{
    this.lstCanal.forEach(element => {
      this.lstCabecera.push(element.nombre);
    });
  }

  getLstLineaCanal(): LineaCanal[] {
    return this.lstLineaCanal;
  }

  getLstLinea(): Linea[] {
    return this.lstLinea;
  }

  getLstCanal(): Canal[] {
    return this.lstCanal;
  }
}
