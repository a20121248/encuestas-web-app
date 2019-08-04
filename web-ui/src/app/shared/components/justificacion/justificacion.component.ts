import { Component, OnInit, Input } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Justificacion } from 'src/app/shared/models/justificacion';
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
  lstJustificaciones: Justificacion[];
  @Input() observaciones: string;
  @Input() justificacion: Justificacion;

  constructor(
    private justificacionService: JustificacionService,
    private router: Router) {
      this.justificacionService.getJustificaciones().subscribe(justificaciones => {
        this.lstJustificaciones = justificaciones;
      });
    }

  ngOnInit() {
  }
}
