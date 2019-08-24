import { Component, OnInit, Input } from '@angular/core';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ProcesoService } from 'src/app/shared/services/proceso.service';
import { Proceso } from 'src/app/shared/models/Proceso';
import { UsuarioService } from 'src/app/shared/services/usuario.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-cargar-usuarios',
  templateUrl: './cargar-usuarios.component.html',
  styleUrls: ['./cargar-usuarios.component.css']
})
export class CargarUsuariosComponent implements OnInit {
  titulo: string;
  cantUsuarios: number;
  fileForm: FormGroup;
  ruta: string;
  @Input() procesos: Proceso[];
  @Input() selectedProceso: Proceso;
  fileToUpload: File;
  fileToDownload: File;
  fileURL: SafeResourceUrl;

  constructor(
    private fileUploadService: FileUploadService,
    private sanitizer: DomSanitizer,
    private usuarioService: UsuarioService
  ) {
    this.titulo = 'CARGAR USUARIOS';
    this.usuarioService.count().subscribe(cantUsuarios => {
      this.cantUsuarios = cantUsuarios;
    });
  }

  ngOnInit() {
  }

  seleccionarArchivo(e) {
    const rutaArr = e.target.value.split('\\');
    this.ruta = rutaArr[rutaArr.length - 1];
  }

  handleFileInput(file: FileList) {
    this.fileToUpload = file.item(0);
    console.log(this.fileToUpload);
  }

  uploadFile() {
    this.fileUploadService.postFile(this.fileToUpload).subscribe(data => {
      console.log(data), err => console.log(err)
    });
  }

  downloadFile() {
    this.fileUploadService.getFile('201907_20190711_Productos.xlsx').subscribe(data => {
      console.log("pre carga");
      let blob = new Blob([data], {type:"application/ms-excel"});
      // let url = window.URL.createObjectURL(blob);
      console.log(blob);
      this.fileURL = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
      // let pwa = window.open(this.fileURL);
      // if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
      //   alert('Please disable your Pop-up blocker and try again.');
      // }

    });
  }

  getLastDateUpdate():any {
    return 0;
  }
}
