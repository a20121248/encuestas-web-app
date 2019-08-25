import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Centro } from 'src/app/shared/models/centro';
import { Area } from 'src/app/shared/models/area';
import { Tipo } from 'src/app/shared/models/tipo';

@Component({
  selector: 'app-grafico-control',
  templateUrl: './grafico-control.component.html',
  styleUrls: ['./grafico-control.component.scss']
})
export class GraficoControlComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;

  @Input() areas: Area[];
  selectedAreas = [];

  @Input() centros: Centro[];
  selectedCentros = [];

  @Input() estados: Tipo[];
  selectedEstados = [];

  titulo = 'GRÁFICO DE CONTROL';

  chartOptions = {
    responsive: true,
    legend: {
      align: 'middle',
      display: true,
      labels: {
        boxWidth: 20,
        padding: 20
      },
      position: 'right'
    }
  };

  chartData = [
    { data: [330, 600, 260], label: 'Cantidad' }
  ];

  chartLabels = ['Completado', 'En curso', 'No accedieron'];

  onChartClick(event) {
    console.log(event);
  }


  constructor() { }

  ngOnInit() {
  }

  actualizar() {

  }

}
