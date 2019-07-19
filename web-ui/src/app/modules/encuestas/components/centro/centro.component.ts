// import { Component, OnInit } from '@angular/core';
// import { Centro } from '../../../../shared/models/centro';
// import { CentroService } from '../../../../shared/services/centro.service';

// import { Observable } from 'rxjs';
// import {HttpClient, HttpHeaders} from '@angular/common/http';
// import { MatTableDataSource } from '@angular/material';

// @Component({
//   selector: 'app-form-centro',
//   templateUrl: './centro.component.html',
//   styleUrls: ['./centro.component.css']
// })

// export class CentroComponent implements OnInit {

//   lstCentros: Centro[];

//   private url: string = 'http://hp840g-malfbl35:8080/api/encuesta/empresas';
  

//   dcCentro = ['id', 'codigo', 'nombre', 'abreviatura', 'porcentaje','fechaCreacion', 'nivel' ];
//   dataSource: MatTableDataSource<Centro>;

//   centro: Centro[] = [
//     { id:  1 , codigo: '30100000', nombre: 'GERENCIA DE ÁREA DE PLANEAMIENTO FINANCIERO Y TÉCNICAS DE NEGOCIOS', abreviatura: 'N/A', porcentaje: 0.2, fechaCreacion: '06-19', nivel: 8  },
//     { id:  1 , codigo: '30101000', nombre: 'EQUIPO DE TÉCNICA DE NEGOCI', abreviatura: 'N/A', porcentaje: 0.2, fechaCreacion: '06-19', nivel: 9  },
//     { id:  1 , codigo: '30101100', nombre: 'GERENCIA DE EFICIENCIA Y PROYECTOS', abreviatura: 'N/A', porcentaje: 0.1, fechaCreacion: '06-19', nivel: 5  },
//     { id:  1 , codigo: '30101110', nombre: 'EQUIPO DE EFICIENCIA', abreviatura: 'N/A', porcentaje: 0.2, fechaCreacion: '06-19', nivel: 5  },
//     { id:  1 , codigo: '30101111', nombre: 'EQUIPO DE GESTIÓN DE PROYECTOS', abreviatura: 'N/A', porcentaje: 0.2, fechaCreacion: '06-19', nivel: 5  },
//     { id:  1 , codigo: '30101200', nombre: 'EQUIPO DE PLANEAMIENTO FINANCIERO', abreviatura: 'N/A', porcentaje: 0.2, fechaCreacion: '06-19', nivel: 5  },

//   ];
  
//   constructor(private centroService: CentroService) {

//     this.dataSource = new MatTableDataSource<Centro>();
  
//     this.dataSource.data = this.centro;
//    }


  
//   ngOnInit() {
//     this.centroService.getCentros().subscribe(
//       // lstCentros => this.lstCentros = lstCentros
//     );
//   }

// }
