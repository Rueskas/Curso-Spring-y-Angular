import { Component, OnInit } from '@angular/core';
import { InvoicesService } from './services/invoices.service';
import { Invoice } from './models/invoice';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html'
})
export class InvoiceDetailComponent implements OnInit {
  title: string = 'Invoice';
  invoice: Invoice;
  constructor(private _invoiceService: InvoicesService,
    private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this._activatedRoute.paramMap.subscribe(paramMap => {
      let id = +paramMap.get('id');
      if (id != null) {
        this._invoiceService.getInvoice(id).subscribe(inv => this.invoice = inv);
      }
    })
  }



}
