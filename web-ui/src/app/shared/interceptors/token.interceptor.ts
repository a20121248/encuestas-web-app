import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService,
              private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    if (this.authService.token != null) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + this.authService.token)
      });
      return next.handle(authReq).pipe(
        catchError((err: HttpErrorResponse) => {
          if (err.status === 0 || err.status === 401) {
            this.authService.logout();
            this.router.navigate(['/login']);
          }
          return throwError(err);
        })
      );
    }
    return next.handle(req);
  }
}
