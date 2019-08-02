import { Component, OnInit, Input } from '@angular/core';

import {HttpClient} from '@angular/common/http';

import { Centro } from 'src/app/shared/models/centro';
import {CentroService} from '../../../../shared/services/centro.service';

@Component({
  selector: 'app-form-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css']
})

export class CentroComponent implements OnInit {
  @Input() lstCentros: Centro[];
  dcCentro = ['codigo','nombre', 'porcentaje'];

  constructor( private centroService: CentroService, private http: HttpClient) { }

  ngOnInit() {
    this.lstCentros = [];
}

  getTotalPorcentaje(){
    return this.lstCentros.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }
}
