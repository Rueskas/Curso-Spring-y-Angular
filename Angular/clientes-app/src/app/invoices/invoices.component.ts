import { Component, OnInit } from '@angular/core';
import { Invoice } from './models/invoice';
import { InvoicesService } from './services/invoices.service';
import { CustomerService } from '../customers/customer.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, flatMap } from 'rxjs/operators';
import { Product } from './models/product';

@Component({
  selector: 'app-invoices',
  templateUrl: './invoices.component.html'
})
export class InvoicesComponent implements OnInit {
  autocompleteControl = new FormControl();
  filteredProducts: Observable<Product[]>;
  title: string = 'New Invoice';
  invoice: Invoice = new Invoice();

  constructor(private _invoiceService: InvoicesService,
    private _customerService: CustomerService,
    private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.filteredProducts = this.autocompleteControl.valueChanges
      .pipe(
        map(value => typeof value === 'string' ? value : value.name),
        flatMap(value => value ? this._filter(value) : [])
      );
    this._activatedRoute.paramMap.subscribe(
      params => {
        let customerId = +params.get('customerId');
        this._customerService.getCustomer(customerId).subscribe(
          customer => this.invoice.customer = customer
        );
      }
    )
  }
  private _filter(value: string): Observable<Product[]> {
    const filterValue = value.toLowerCase();

    return this._invoiceService.findProducts(filterValue);
  }

  showName(product?: Product): string {
    return product ?.name;
  }

  createInvoice(): void {

  }

}
