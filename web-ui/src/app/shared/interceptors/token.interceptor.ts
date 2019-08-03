import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    if (this.authService.token != null) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + this.authService.token)
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}
