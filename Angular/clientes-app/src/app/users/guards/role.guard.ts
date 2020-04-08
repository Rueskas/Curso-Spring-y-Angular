import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private _authService: AuthService,
    private _router: Router) { };
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!this._authService.isAuthenticated()) {
      swal.fire('Unauthorized', 'Please, Login to continue', 'error');
      this._router.navigate(['/login']);
      return false;
    }

    let role = next.data['role'] as string;
    if (this._authService.hasRole(role)) {
      return true;
    } else {
      swal.fire('Unauthorized', 'You can not access to this resource', 'warning');
      this._router.navigate(['/customers']);
      return false;
    }
  }

}
