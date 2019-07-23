import { Component, OnInit } from '@angular/core';
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
  private lstEps: Eps[] = [];

  dcEps = ['nombre', 'porcentaje'];

  constructor( private epsService: EpsService, 
    private http: HttpClient) { }

  ngOnInit() {
    this.epsService.getEps().subscribe(eps=>{ 
      this.lstEps=eps;
      }
    );
  }

  editField: number;
  
  getLstEps(){
    return this.lstEps;
  }

  getTotalPorcentaje(){
    return this.lstEps.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
  }
}
