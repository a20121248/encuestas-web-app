import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { UsuarioService } from './usuario.service';

@Injectable({
  providedIn: 'root'
})
export class EncuestaService {
  currentColaborador: Usuario;

  constructor(private usuarioService: UsuarioService) { }

  loadColaborador(posicionCodigo: string) {
    this.usuarioService.getUsuarioByPosicionCodigo(posicionCodigo).subscribe(usuario => {
        this.currentColaborador = usuario;
    });
  }

  setColaborador(colaborador: Usuario) {
    this.currentColaborador = colaborador;
  }
}
