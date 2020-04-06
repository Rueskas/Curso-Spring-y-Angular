import { Injectable } from '@angular/core';
import { formatDate, DatePipe } from '@angular/common';
import { Customer } from './customer';
//import { CUSTOMERS } from './customers.json';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { Global } from '../../assets/global';
import { map, catchError, tap } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router'


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private httpHeaders = new HttpHeaders({ 'Content-type': 'application/json' })

  constructor(private _http: HttpClient, private _router: Router) { }

  public getCustomers(page: number = 0): Observable<any> {
    //return of(CUSTOMERS);
    //return this._http.get<Customer[]>(Global.url + '/customers');

    return this._http.get(Global.url + '/customers/page/' + page);
  }

  public getCustomer(id: number): Observable<Customer> {
    return this._http.get<Customer>(Global.url + '/customers/' + id)
      .pipe(
        map(response => response = new Customer(
          (response as any).customer.id, (response as any).customer.name,
          (response as any).customer.surname, (response as any).customer.email, (response as any).customer.createdAt)),
        catchError(e => {
          console.error(e.error.message);
          swal.fire(e.error.message, e.error.error, 'error');
          return throwError(e);
        }));
  }

  public postCustomer(customer: Customer): Observable<any> {
    return this._http.post<any>(
      Global.url + "/customers", customer, { headers: this.httpHeaders }).pipe(
        map(response => response = new Customer(
          (response as any).customer.id, (response as any).customer.name,
          (response as any).customer.surname, (response as any).customer.email, (response as any).customer.createdAt)),
        catchError(e => {
          if (e.status == 400) {
            return throwError(e);
          }
          console.error(e.error.message);
          swal.fire(e.error.message, e.error.error, 'error');
          return throwError(e);
        })
      );
  }

  public putCustomer(customer: Customer): Observable<Customer> {
    return this._http.put(
      Global.url + "/customers/" + customer.getId(), customer,
      { headers: this.httpHeaders }).pipe(
        map(response => response = new Customer(
          (response as any).customer.id, (response as any).customer.name,
          (response as any).customer.surname, (response as any).customer.email, (response as any).customer.createdAt)),
        catchError(e => {
          if (e.status == 400) {
            return throwError(e);
          }
          console.error(e.error.message);
          swal.fire(e.error.message, e.error.error, 'error');
          return throwError(e);
        })
      );
  }

  public deleteCustomer(id: number): Observable<any> {
    return this._http.delete<any>(
      Global.url + "/customers/" + id, { headers: this.httpHeaders })
      .pipe(catchError(e => {
        console.error(e.error.message);
        swal.fire(e.error.error, e.error.message, 'error');
        return throwError(e);
      }));
  }

  public uploadAvatar(file: File, id: string): Observable<HttpEvent<{}>> {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("id", id);

    const req = new HttpRequest('POST', Global.url + "/customers/upload", formData, {
      reportProgress: true
    });

    return this._http.request(req);
  }
}
