import { Injectable } from '@angular/core';
import { Empresa } from '../models/empresa';
import { of, Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
private url:string = 'http://hp840g-malfbl35:8080/api/empresas';

  constructor(private http: HttpClient) { }

  listEmpresas: Empresa[] = [];

  getEmpresas(): Observable<Empresa[]> {
    // return of(this.listEmpresas);
    return this.http.get<Empresa[]>(this.url);
  }
}
