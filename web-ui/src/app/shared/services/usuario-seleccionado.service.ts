import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioSeleccionadoService {
  private usuario = new BehaviorSubject<Usuario>(null);
  usuarioActual = this.usuario.asObservable();
  constructor() { }

  setUsuarioSeleccionado(usuarioEscogido: Usuario) {
    this.usuario.next(usuarioEscogido);
  }
}
