import { Component, OnInit } from '@angular/core';
import * as jQuery from 'jquery';


@Component({
  selector: 'app-form-justificacion',
  templateUrl: './justificacion.component.html',
  styleUrls: ['./justificacion.component.css']
})
export class JustificacionComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    $(document).ready(function(){
      // jQuery('.chosen-select').chosen();
    });
  }
}
