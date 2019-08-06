import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';

@Component({
  selector: 'app-cargar-usuarios',
  templateUrl: './cargar-usuarios.component.html',
  styleUrls: ['./cargar-usuarios.component.css']
})
export class CargarUsuariosComponent implements OnInit {

  // @ViewChild('fileInput') fileInput: ElementRef;


  uploader: FileUploader;
  constructor() { }
  

  ngOnInit() {
    const headers = [{name: 'Accept', value: 'application/json'}];
    this.uploader = new FileUploader({url: 'localhost:8080/api/files', autoUpload: true, headers: headers});
    console.log(this.uploader.options.itemAlias);
    this.uploader.onCompleteAll = () => alert('File uploaded');
  }

  getLastDateUpdate():any{
    return 0;
  }
}
