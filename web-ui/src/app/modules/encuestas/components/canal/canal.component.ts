import { Component, OnInit, Input } from "@angular/core";
import { Linea } from "src/app/shared/models/linea";
import { LineaCanal } from 'src/app/shared/models/linea-canal';
import { ObjetoObjetos } from 'src/app/shared/models/objeto-objetos';

@Component({
  selector: "app-form-canal",
  templateUrl: "./canal.component.html",
  styleUrls: ["./canal.component.css"]
})
export class CanalComponent implements OnInit {

  @Input() lineaSeleccionada: ObjetoObjetos;
  dcLinea = ["codigo", "nombre", "porcentaje","cumplimentar"];
  constructor() {}

  ngOnInit() {
    console.log(this.lineaSeleccionada);
  }

  getTotalPorcentaje() {
    if (this.lineaSeleccionada.lstObjetos != null) {
      return this.lineaSeleccionada.lstObjetos.map(t => t.porcentaje).reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }
}
