import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FileService } from 'src/app/shared/services/file.service';
import * as fileSaver from 'file-saver'; // npm i --save file-saver

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements OnInit {
  titulo = 'Reporting';
  constructor(
    private titleService: Title,
    private fileService: FileService) { }

  ngOnInit() {
    this.titleService.setTitle('Encuestas | Reporting');
  }

  descargar() {
    this.fileService.downloadFile('test.csv').subscribe(response => {
      console.log('recibien el response:');
      console.log(response);

      this.saveFile(response.body);
    });
  }

  saveFile(data: any) {
    const blob = new Blob([data], {type: 'text/csv; charset=utf-8'});
    fileSaver.saveAs(blob, 'aaa.csv');
  }

}
