import { Component, OnInit } from '@angular/core';
import { FileService } from 'src/app/shared/services/file.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-descarga-log',
  templateUrl: './descarga-log.component.html',
  styleUrls: ['./descarga-log.component.scss']
})
export class DescargaLogComponent implements OnInit {

  constructor(
    private fileService:FileService
  ) {
  }

  ngOnInit() {
    const filename = 'app.log';
    this.fileService.descargarLogControl().subscribe(response => {
      fileSaver.saveAs(new Blob([response], { type: 'plain/text' }), filename);
    }, err => {
      console.log(err);
    });
  }

}
