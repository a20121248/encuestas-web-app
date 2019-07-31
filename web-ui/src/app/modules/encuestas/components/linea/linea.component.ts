import { Component, OnInit, Input } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Linea } from "src/app/shared/models/linea";
import { LineaService } from "../../../../shared/services/linea.service";

@Component({
  selector: "app-form-linea",
  templateUrl: "./linea.component.html",
  styleUrls: ["./linea.component.css"]
})
export class LineaComponent implements OnInit {
  @Input() lstLineas: Linea[];

  dcLinea = ["codigo", "nombre", "porcentaje"];

  constructor(private lineaService: LineaService, private http: HttpClient) {}

  ngOnInit() {
    this.lstLineas = [];
  }

  getTotalPorcentaje() {
    return this.lstLineas
      .map(t => t.porcentaje)
      .reduce((acc, value) => acc + value, 0);
  }
}
