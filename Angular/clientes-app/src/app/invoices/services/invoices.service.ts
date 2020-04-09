import { Injectable } from '@angular/core';
import { Global } from '../../global';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Invoice } from '../models/invoice';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class InvoicesService {

  private url: string = Global.URL + "/api/invoices";

  constructor(private _http: HttpClient,
    private _router: Router) { }

  public getInvoice(id: number): Observable<Invoice> {
    return this._http.get<Invoice>(this.url + "/" + id).pipe(
      catchError(e => {
        if (e.status == 404) {
          swal.fire(e.error.error, e.error.message, 'error');
          this._router.navigate(['/customers']);
        }
        return throwError(e);
      }));
  }

  public deleteInvoice(id: number): Observable<void> {
    return this._http.delete<void>(this.url + "/" + id);
  }

  public findProducts(filter: string): Observable<Product[]> {
    return this._http.get<Product[]>(this.url + "/filter-products/" + filter);
  }

  public postInvoice(invoice: Invoice): Observable<Invoice> {
    console.log(invoice);
    return this._http.post<Invoice>(this.url, invoice);
  }
}
