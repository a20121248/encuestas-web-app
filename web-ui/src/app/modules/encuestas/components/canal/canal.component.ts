import { Component, OnInit, Input } from "@angular/core";
import { Linea } from "src/app/shared/models/linea";
import { LineaCanal } from 'src/app/shared/models/linea-canal';

@Component({
  selector: "app-form-canal",
  templateUrl: "./canal.component.html",
  styleUrls: ["./canal.component.css"]
})
export class CanalComponent implements OnInit {

  @Input() lineaSeleccionada: LineaCanal;
  dcLinea = ["codigo", "nombre", "porcentaje","cumplimentar"];
  constructor() {}

  ngOnInit() {}

  getTotalPorcentaje() {
    if (this.lineaSeleccionada.lstCanales != null) {
      return this.lineaSeleccionada.lstCanales.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }
}
