import { Component, OnInit } from '@angular/core';
import { Centro } from '../../../../shared/models/centro';
import { CentroService } from '../../../../shared/services/centro.service';

@Component({
  selector: 'app-form-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css']
})
export class CentroComponent implements OnInit {

  lstCentros: Centro[];

  constructor(private centroService: CentroService) { }

  ngOnInit() {
    this.centroService.getCentros().subscribe(
      lstCentros => this.lstCentros = lstCentros
    );
  }

}
