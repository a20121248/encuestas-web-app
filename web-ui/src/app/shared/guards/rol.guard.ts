import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, CanLoad,
         Route, UrlSegment, ActivatedRouteSnapshot,
         RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RolGuard implements CanActivate, CanActivateChild, CanLoad {
  constructor(public authService: AuthService,
              private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (!this.authService.isAuthenticated()) {
        this.router.navigate(['/login']);
        return false;
      }
      const rol = next.data.rol as string;
      if (this.authService.hasRole(rol)) {
        return true;
      }
      swal.fire('Acceso denegado', `No tienes acceso a este recurso.`, 'warning');
      this.router.navigate(['/colaboradores']);
      return false;
  }
  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }
  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }
}
