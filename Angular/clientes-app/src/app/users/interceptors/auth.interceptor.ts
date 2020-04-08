import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
import swal from 'sweetalert2';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private _authService: AuthService,
    private _router: Router) { };

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError(e => {
        if (e.status == 401) {
          if (this._authService.isAuthenticated()) {
            this._authService.logout();
          }
          swal.fire('Unauthorized', 'Please, Login to continue', 'error');
          this._router.navigate(['/login']);
        } else if (e.status == 403) {
          swal.fire('Unauthorized', 'You can not access to this resource', 'warning');
          this._router.navigate(['/customers']);
        }
        return throwError(e);
      })
    );
  }

}
