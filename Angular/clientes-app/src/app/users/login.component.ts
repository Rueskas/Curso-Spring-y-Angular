import { Component, OnInit } from '@angular/core';
import { User } from './user';
import swal from 'sweetalert2';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  title: string = "Login"
  user: User;
  constructor(private _authService: AuthService, private _router: Router) { }

  ngOnInit(): void {
    this.user = new User();
    if (this._authService.isAuthenticated()) {
      swal.fire("Welcome " + this._authService.user.username,
        'You have logged in succesafully', 'info');
      this._router.navigate(['/customers']);
    }
  }

  login(): void {
    if (this.user.username == null || this.user.password == null) {
      swal.fire('Error Logging in', 'User and password can not to be empty', 'error');
    } else {
      console.log(this.user);
      this._authService.login(this.user).pipe(
        catchError(e => {
          swal.fire('Error logging in', 'Username or password incorrect', 'error');
          return throwError(e);
        })
      ).subscribe(response => {

        this._authService.saveUser(response.access_token);
        this._authService.saveToken(response.access_token);

        let user = this._authService.user;
        swal.fire('Welcome ' + user.username, 'You have logged successfully', 'success');
        this._router.navigate(['/customers']);
      })
    }
  }

}
