import { Component, OnInit } from '@angular/core';
import { Invoice } from './models/invoice';
import { InvoicesService } from './services/invoices.service';
import { CustomerService } from '../customers/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, flatMap } from 'rxjs/operators';
import { Product } from './models/product';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { InvoiceItem } from './models/invoice-item';
import swal from 'sweetalert2';

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
    private _activatedRoute: ActivatedRoute,
    private _router: Router) { }

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

  selectProduct(event: MatAutocompleteSelectedEvent): void {
    let product = event.option.value as Product;
    if (this.existsItem(product.id)) {
      this.incrementAmount(product.id);
    } else {
      let newItem = new InvoiceItem();
      newItem.product = product;
      this.invoice.items.push(newItem);
    }
    this.autocompleteControl.setValue('');
    event.option.focus();
    event.option.deselect();
  }

  updateAmount(id: number, event): void {
    let amount = event.target.value as number;
    if (amount == 0) {
      this.removeItem(this.invoice.items.filter(i => i.product.id == id)[0]);
    } else {
      this.invoice.items =
        this.invoice.items.map(i => {
          i.amount = i.product.id == id ? amount : i.amount;
          return i;
        })
    }
  }

  existsItem(id: number): boolean {
    return this.invoice.items.filter(i => i.product.id == id).length > 0 ? true : false;
  }

  incrementAmount(id: number) {
    this.invoice.items =
      this.invoice.items.map(i => {
        i.amount = i.product.id == id ? i.amount + 1 : i.amount;
        return i;
      })
  }

  removeItem(item: InvoiceItem) {
    const index: number = this.invoice.items.indexOf(item);
    if (index !== -1) {
      this.invoice.items.splice(index, 1);
    }
  }

  createInvoice(invoiceForm) {
    if (!invoiceForm.invalid && this.invoice.items.length != 0) {
      this._invoiceService.postInvoice(this.invoice).subscribe(
        invoice => {
          swal.fire('Created', 'Invoice has been created successfully', 'success');
          this._router.navigate(['/invoices', invoice.id]);
        }
      )
    }

  }
}
