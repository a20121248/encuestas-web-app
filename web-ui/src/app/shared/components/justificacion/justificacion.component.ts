import { Component, OnInit } from '@angular/core';
import { Justificacion } from '../../../shared/models/justificacion';

import {FormControl, Validators} from '@angular/forms';
import { ViewChild } from '@angular/core';
import { JustificacionService } from 'src/app/shared/services/justificacion.service';
import { Router } from '@angular/router';


export interface Justificacion {
  nombre: string;
  id: number;
}

@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.css']
})
export class JustificacionComponent implements OnInit {
  
  justificacionControl = new FormControl('', [Validators.required]);
  selectFormControl = new FormControl('', Validators.required);
  lstJustificaciones: Justificacion[];
  
  constructor(private justificacionService: JustificacionService,
    private router: Router) { }

    ngOnInit() {
      this.justificacionService.getJustificaciones().subscribe(justificaciones=>{ 
        this.lstJustificaciones=justificaciones;
        }
      );
    }

    getLstEmpresas(){
      return this.lstJustificaciones;
    }
}
