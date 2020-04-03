import { Injectable } from '@angular/core';
import { Customer } from './customer';
import { CUSTOMERS } from './customers.json';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Global } from '../../assets/global';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  constructor(private _http: HttpClient) { }

  public getCustomers(): Observable<Customer[]> {
    //CUSTOMERS.forEach(c => console.log(c));
    //return of(CUSTOMERS);
    return this._http.get<Customer[]>(Global.url + '/customers');

    /*return this._http.get(Global.url + '/customers').pipe(
      map(response => {
        return response as Customer[];
      })
    );
		*/
  }
}
