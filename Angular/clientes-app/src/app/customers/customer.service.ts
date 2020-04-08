import { Injectable } from '@angular/core';
import { formatDate, DatePipe } from '@angular/common';
import { Customer } from './customer';
//import { CUSTOMERS } from './customers.json';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { Global } from '../../assets/global';
import { map, catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router'
import { Region } from './region';
import { AuthService } from '../users/auth.service';
import swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  //private httpHeaders = new HttpHeaders({ 'Content-type': 'application/json' })

  constructor(private _http: HttpClient,
    private _router: Router,
    private _authService: AuthService) { }

  public getCustomers(page: number = 0): Observable<any> {
    //return of(CUSTOMERS);
    //return this._http.get<Customer[]>(Global.URL + '/customers');

    return this._http.get(Global.URL + '/api/customers/page/' + page);
  }

  public getRegions(): Observable<Region[]> {
    return this._http.get<Region[]>(Global.URL + '/api/customers/regions');
  }

  public getCustomer(id: number): Observable<Customer> {
    return this._http.get<Customer>(Global.URL + '/api/customers/' + id)
      .pipe(
        map((response: any) => response.customer as Customer),
        catchError(e => {
          if (e.status != 401) {
            swal.fire(e.error.message, e.error.error, 'error');
          }
          return throwError(e);
        }));
  }

  public postCustomer(customer: Customer): Observable<any> {
    return this._http.post<any>(
      Global.URL + "/api/customers", customer).pipe(
        map((response: any) => response.customer as Customer),
        catchError(e => {
          if (e.status == 400) {
            swal.fire(e.error.message, e.error.error, 'error');
            return throwError(e);
          }
          return throwError(e);
        })
      );
  }

  public putCustomer(customer: Customer): Observable<Customer> {
    return this._http.put(
      Global.URL + "/api/customers/" + customer.id, customer).pipe(
        map((response: any) => response.customer as Customer),
        catchError(e => {
          if (e.status == 400) {
            swal.fire(e.error.message, e.error.error, 'error');
            return throwError(e);
          }
          return throwError(e);
        })
      );
  }

  public deleteCustomer(id: number): Observable<any> {
    return this._http.delete<any>(
      Global.URL + "/api/customers/" + id)
      .pipe(catchError(e => {
        swal.fire(e.error.error, e.error.message, 'error');
        return throwError(e);
      }));
  }

  public uploadAvatar(file: File, id: string): Observable<HttpEvent<{}>> {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("id", id);
    const req = new HttpRequest('POST', Global.URL + "/api/customers/upload", formData, {
      reportProgress: true
    });

    return this._http.request(req);
  }
}
