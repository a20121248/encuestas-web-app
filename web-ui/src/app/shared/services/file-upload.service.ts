import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private endpoint = 'http://localhost:8080/api';
  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }
  
  postFile(fileToUpload: File): Observable<any> {
    // const url = `procesos/${this.authService.proceso.id}/colaboradores/${usuario.posicion.codigo}/encuesta/empresas`;
    const url =`${this.endpoint}/upload-files`;
    let formdata: FormData = new FormData();
    formdata.append('file', fileToUpload);
    const req = new HttpRequest('POST', url, formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }

  getFile(filename: string): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'text/csv; charset=utf-8');
    const url =`${this.endpoint}/download-files/${filename}`;
    return this.http.get(url,{
      headers: headers,
      observe: 'response',
      responseType: 'text'
    });
  }
}
