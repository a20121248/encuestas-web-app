import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Router, ActivatedRoute } from "@angular/router";

import { Linea } from "src/app/shared/models/linea";
import { LineaService } from "../../../../shared/services/linea.service";

@Component({
  selector: "app-form-linea",
  templateUrl: "./linea.component.html",
  styleUrls: ["./linea.component.css"]
})
export class LineaComponent implements OnInit {
  @Input() lstLineas: Linea[];
  @Output() sendLinea = new EventEmitter();

  dcLinea = ["codigo", "nombre", "porcentaje", "completar"];

  constructor() {}

  ngOnInit() {
    this.lstLineas = [];
}

  showCanalesBylineaBoton(linea: Linea) {
    this.sendLinea.emit(linea);
  }

  revisarPerfil(): string {
    if (true) {
      return 'linea-canal';

    } else  {
      return 'linea-subcanal';
    }
  }

  getTotalPorcentaje() {
    if (this.lstLineas != null) {
      return this.lstLineas
        .map(t => t.porcentaje)
        .reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }


}
