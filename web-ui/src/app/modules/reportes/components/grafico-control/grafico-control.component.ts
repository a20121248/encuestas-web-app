import { Component, OnInit, Input } from '@angular/core';
import { Proceso } from 'src/app/shared/models/proceso';
import { Centro } from 'src/app/shared/models/centro';
import { Area } from 'src/app/shared/models/area';

@Component({
  selector: 'app-grafico-control',
  templateUrl: './grafico-control.component.html',
  styleUrls: ['./grafico-control.component.css']
})
export class GraficoControlComponent implements OnInit {
  @Input() procesos: Proceso[];
  @Input() areas: Area[];
  @Input() centros: Centro[];
  @Input() selectedProceso: Proceso;
  selectedAreas = [];
  selectedCentros = [];
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

}
