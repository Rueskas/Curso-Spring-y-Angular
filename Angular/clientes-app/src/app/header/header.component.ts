import { Component } from '@angular/core';
import { AuthService } from '../users/auth.service';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  title: string = 'App Angular';

  constructor(public authService: AuthService, private _router: Router) {

  }

  logout(): void {
    this.authService.logout();
    swal.fire('Bye', 'See you', 'info');
    this._router.navigate(['/login']);
  }
}
