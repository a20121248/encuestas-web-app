import { Component, OnInit } from '@angular/core';
import { PerfilService } from 'src/app/shared/services/perfil.service';

@Component({
  selector: 'app-perfiles',
  templateUrl: './perfiles.component.html',
  styleUrls: ['./perfiles.component.scss']
})
export class PerfilesComponent implements OnInit {

  constructor(
    public perfilService: PerfilService
  ) { }

  ngOnInit() {
  }
}
