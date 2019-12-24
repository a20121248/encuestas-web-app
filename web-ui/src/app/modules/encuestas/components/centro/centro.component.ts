import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { FormGroup, Validators, FormControl, AbstractControl } from '@angular/forms';
import { Centro } from 'src/app/shared/models/centro';
import { Usuario } from 'src/app/shared/models/usuario';
import { CustomValidatorsService } from 'src/app/shared/services/custom-validators.service';
import { SharedFormService } from 'src/app/shared/services/shared-form.service';

@Component({
  selector: 'app-form-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.scss']
})

export class CentroComponent implements OnInit {
  @Input() lstCentros: Centro[];
  @Input() usuarioSeleccionado: Usuario;
  @Input() haGuardado: boolean;

  @Output() estadoFormCentroToParent = new EventEmitter();
  @Output() sendDownloadToParent = new EventEmitter();

  dcCentro = ['codigo', 'nombre', 'nivel', 'porcentaje'];
  dataSource = new MatTableDataSource<Centro | Group>([]);
  groupByColumns: string[] = ['grupo'];
  groupForm: FormGroup;
  porcTotal: number;


  constructor(private sharedFormService: SharedFormService) {
    this.groupForm = new FormGroup({});
  }

  ngOnInit() {
    this.dataSource.data = this.addGroups(this.lstCentros,this.groupByColumns);
    this.dataSource.filterPredicate = this.customFilterPredicate.bind(this);
    this.dataSource.data.map(row => {
      this.groupHeaderClick(row);
    });
    this.onChanges();
  }

  customFilterPredicate(data: Centro | Group, filter: string): boolean {
    return (data instanceof Group) ? data.visible : this.getDataRowVisible(data);
  }

  getDataRowVisible(data: Centro): boolean {
    const groupRows = this.dataSource.data.filter(
      row => {
        if (!(row instanceof Group)) return false;

        let match = true;
        this.groupByColumns.forEach(
          column => {
            if (!row[column] || !data[column] || row[column] !== data[column]) match = false;
          }
        );
        return match;
      }
    );

    if (groupRows.length === 0) return true;
    if (groupRows.length > 1) throw "Data row is in more than one group!";
    const parent = <Group>groupRows[0];  // </Group> (Fix syntax coloring)

    return parent.visible && parent.expanded;
  }

  groupHeaderClick(row) {
    row.expanded = !row.expanded
    this.dataSource.filter = performance.now().toString();  // hack to trigger filter refresh
  }

  addGroups(data: any[], groupByColumns: string[]): any[] {
    var rootGroup = new Group();
    return this.getSublevel(data, 0, groupByColumns, rootGroup);
  }

  getSublevel(data: any[], level: number, groupByColumns: string[], parent: Group): any[] {
    // Recursive function, stop when there are no more levels.
    if (level >= groupByColumns.length)
      return data;

    var groups = this.uniqueBy(
      data.map(
        row => {
          var result = new Group();
          result.level = level + 1;
          result.parent = parent;
          for (var i = 0; i <= level; i++)
            result[groupByColumns[i]] = row[groupByColumns[i]];
          return result;
        }
      ),
      JSON.stringify);

    const currentColumn = groupByColumns[level];

    var subGroups = [];
    groups.forEach(group => {
      let rowsInGroup = data.filter(row => group[currentColumn] === row[currentColumn])
      let subGroup = this.getSublevel(rowsInGroup, level + 1, groupByColumns, group);
      subGroup.unshift(group);
      subGroups = subGroups.concat(subGroup);
    })
    return subGroups;
  }

  uniqueBy(a, key) {
    var seen = {};
    return a.filter(function (item) {
      var k = key(item);
      return seen.hasOwnProperty(k) ? false : (seen[k] = true);
    })
  }

  isGroup(index, item): boolean {
    return item.level;
  }

  validacionItemControl(value:number):AbstractControl{
    return this.groupForm.get(String(value));
  }

  getTotalPorcentaje() {
    if (this.lstCentros != null) {
      this.porcTotal = Math.round(this.lstCentros.map(t => 100*t.porcentaje).reduce((acc, value) => acc + value, 0))/100;
      return this.porcTotal;
    }
    else {
      this.porcTotal = 0;
      return this.porcTotal;
    }
  }

  getTotalPorcentajeByGrupo( grupo:string ){
    return Math.round(this.lstCentros.filter((item) => item.grupo == grupo).map(t => 100*t.porcentaje).reduce((acc,value) => acc + value, 0))/100;
  }

  sendEstado(value: boolean) {
    this.estadoFormCentroToParent.emit(value);
  }

  onChanges():void{
    this.groupForm.valueChanges
    .subscribe(data =>{
      if(this.groupForm.valid && this.porcTotal==100){
        this.sendEstado(true);
      } else {
        this.sendEstado(false);
      }
    });
    this.sharedFormService.actualizarEstadoForm1(this.groupForm);
    this.sharedFormService.actualizarPorcentajeForm1(this.porcTotal);
  }

  verificarLista(): boolean {
    if (this.lstCentros != null) { //Verifica la carga de informacion desde el Parent
      for (let centro of this.lstCentros.map(t => t)) {
        let control: FormControl = new FormControl(centro.porcentaje, Validators.compose([
          Validators.required, CustomValidatorsService.validarNegativo, CustomValidatorsService.validarPatronPorcentaje]));
        this.groupForm.addControl(String(centro.id), control);
      }
      return true;
    } else {
      return false;
    }
  }

  descargar(): void {
    this.sendDownloadToParent.emit();
  }
}

export class Group {
  level: number = 0;
  parent: Group;
  expanded: boolean = true;
  get visible(): boolean {
    return !this.parent || (this.parent.visible && this.parent.expanded);
  }
}
