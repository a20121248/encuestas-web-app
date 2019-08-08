import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EpsService} from '../../../../shared/services/eps.service';

//-------------------COMPONENTES LOCALES----------------------------------
import { Eps } from 'src/app/shared/models/eps';



@Component({
  selector: 'app-form-eps',
  templateUrl: './eps.component.html',
  styleUrls: ['./eps.component.css']
})
export class EpsComponent implements OnInit {
  @Input() lstEps: Eps[];

  dcEps = ['nombre', 'porcentaje'];

  constructor() { }

  ngOnInit() {
  this.lstEps = [];
  }

  getTotalPorcentaje(){
    return this.lstEps.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }
}
