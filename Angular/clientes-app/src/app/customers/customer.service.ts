import { Injectable } from '@angular/core';
import { Customer } from './customer';
//import { CUSTOMERS } from './customers.json';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Global } from '../../assets/global';
//import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private httpHeaders = new HttpHeaders({ 'Content-type': 'application/json' })

  constructor(private _http: HttpClient) { }

  public getCustomers(): Observable<Customer[]> {
    //return of(CUSTOMERS);
    return this._http.get<Customer[]>(Global.url + '/customers');

    /*return this._http.get(Global.url + '/customers').pipe(
      map(response => {
        return response as Customer[];
      })
    );
		*/
  }

  public getCustomer(id: number): Observable<Customer> {
    return this._http.get<Customer>(Global.url + '/customers/' + id);
  }

  public postCustomer(customer: Customer): Observable<Customer> {
    return this._http.post<Customer>(
      Global.url + "/customers", customer, { headers: this.httpHeaders });
  }

  public putCustomer(customer: Customer): Observable<Customer> {
    return this._http.put<Customer>(
      Global.url + "/customers/" + customer.getId(), customer,
      { headers: this.httpHeaders });
  }

  public deleteCustomer(id: number): Observable<Customer> {
    return this._http.delete<Customer>(
      Global.url + "/customers/" + id, { headers: this.httpHeaders });
  }
}
