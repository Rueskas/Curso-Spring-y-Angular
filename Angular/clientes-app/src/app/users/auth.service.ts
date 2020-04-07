import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Global } from '../../assets/global';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _user: User;
  private _token: string;

  constructor(private _http: HttpClient) { }

  public get user(): User {
    if (this._user != null) {
      return this._user;
    } else if (sessionStorage.getItem('user')) {
      this._user = JSON.parse(sessionStorage.getItem('user')) as User;
      return this._user;
    } else {
      return new User();
    }
  }

  public get token(): string {
    if (this._token != null) {
      return this._token;
    } else if (sessionStorage.getItem('token')) {
      this._token = sessionStorage.getItem('user');
      return this._token;
    } else {
      return null;
    }
  }

  login(user: User): Observable<any> {
    const urlEndPoint: string = Global.URL + "/oauth/token";
    const credentials: string = btoa(Global.CLIENT_ID + ':' + Global.PASSWORD);
    const httpHeaders = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + credentials
    });
    let params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', user.username);
    params.set('password', user.password);
    return this._http.post(urlEndPoint, params.toString(), { headers: httpHeaders });
  }

  saveUser(access_token: string) {
    let payload = this.getDataFromToken(access_token);
    this._user = new User();
    this._user.name = payload.name;
    this._user.surname = payload.surname;
    this._user.email = payload.email;
    this._user.username = payload.user_name;
    this._user.roles = payload.authorities;
    sessionStorage.setItem("user", JSON.stringify(this._user));
  }
  saveToken(access_token: string) {
    this._token = access_token;
    sessionStorage.setItem("token", this._token);
  }

  getDataFromToken(token: string): any {
    if (token != null) {
      return JSON.parse(atob(token.split(".")[1]));
    }
    return null;
  }
}
