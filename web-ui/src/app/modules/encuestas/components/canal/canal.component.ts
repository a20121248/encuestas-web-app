import { Component, OnInit, Input } from "@angular/core";
import { Linea } from "src/app/shared/models/linea";

@Component({
  selector: "app-form-canal",
  templateUrl: "./canal.component.html",
  styleUrls: ["./canal.component.css"]
})
export class CanalComponent implements OnInit {

  @Input() lineaSeleccionada: Linea;

  constructor() {}

  ngOnInit() {}
}
